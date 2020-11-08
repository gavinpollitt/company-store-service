package custq;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.DependsOn;

/**
 * 
 * @author regen
 *
 *         The service responsible maintaining the queue and its related backing
 *         store
 */
@RestController
@RequestMapping("/queueService")
@DependsOn({"QConfig"})
public class QService {
       	private Logger logger = LoggerFactory.getLogger(QService.class);

	@Autowired
	private CompanyRepository companyRepository;

	private BlockingQueue<Company> companyQueue;

	/**
	 * Remove all messages from the queue
	 */
	public void purge() {
		companyQueue.clear();
	}

	/**
	 * 
	 * @param c The company to add
	 * @throws QueueFullException
	 */
	@RequestMapping(value = "/add", consumes = "application/json", method = RequestMethod.POST)
	public void add(@RequestBody final Company c) throws QueueFullException {
		boolean added = false;
		try {
			added = companyQueue.offer(c, QConfig.getTimeout() == null ? 0 : QConfig.getTimeout(), TimeUnit.SECONDS);
			companyRepository.save(c);
		} catch (Exception e) {
			added = false;
		}

		if (!added) {
			throw new QueueFullException();
		}
               logger.info("Addition of company:" + c.getName() + " confirmed by queue");
	}

	/**
	 * 
	 * @param c       The company to add
	 * @param timeout How long to wait if the queue is empty
	 * @throws QueueEmptyException
	 * @returns The company to add
	 */
	@RequestMapping(value = "/get", produces="application/json",  method = RequestMethod.GET)
	public Company get() throws QueueEmptyException {
		Company got = null;

		try {
			got = companyQueue.poll(QConfig.getTimeout() == null ? 0 : QConfig.getTimeout(), TimeUnit.SECONDS);
			companyRepository.delete(got);
		} catch (Exception e) {
		}

		if (got == null) {
			throw new QueueEmptyException();
		}

                logger.info("Rerieval of company:" + got.getName() + " confirmed by queue");
		return got;
	}

	/**
	 * 
	 * @param sz the maximum number of companies to retrieve in the group
	 * @return A list of the companies identified
	 */
	@RequestMapping(value = "/getGroup/{size}", produces="application/json", method = RequestMethod.GET)
	public List<Company> getGroup(@PathVariable int size) {
		if (size < 1)
			throw new IllegalArgumentException("A group size must be greater than 1");

		List<Company> companies = new ArrayList<>(size);

		if (companyQueue.drainTo(companies, size) > 0) {
			companyRepository.deleteAll(companies);
		}

		return companies;
	}

	/**
	 * Re-synchronise the queue with its underlying data store
	 * 
	 * @throws Exception
	 */
	@PostConstruct
	public void synchronise() throws Exception {
		if (this.companyQueue == null) {
			companyQueue = new ArrayBlockingQueue<>(QConfig.getMaxEntries());
		} else {
			this.purge();
		}
		Iterable<Company> companies = this.companyRepository.findAll();

		for (Company c : companies) {
			companyQueue.offer(c);
			System.out.println("Recovered " + c.getName());
		}
	}
}
