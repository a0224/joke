package com.joke.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.joke.pojo.JokePojo;
import com.joke.pojo.NewsPojo;
import com.joke.pojo.SexyPojo;

@Service
public class QuartzService {
	
	@Autowired
	JokeService jokeServ;
	
	@Autowired
	NewsService newsServ;
	
	@Autowired
	SexyService sexyServ;

	/** log */
	private static Log log = LogFactory.getLog(QuartzService.class);

	public void jokeQuartz() {
		JokePojo in = new JokePojo();
		in.setType(1);
		in.setUserId(1);
		log.info("jokeQuartz");
		jokeServ.addBatch(in);
	}

	public void newsQuartz() {
		NewsPojo in = new NewsPojo();
		in.setType(2);
		log.info("newsQuartz");
		newsServ.addBatch(in);
	}

	public void sexQuartz() {
		SexyPojo in = new SexyPojo();
		in.setType(3);
		log.info("sexQuartz");
		sexyServ.addBatch(in);
	}

}
