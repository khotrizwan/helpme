package com.helpme;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class HelpMeApplication  extends SpringBootServletInitializer {

	public static void main(String[] args) {
//		SpringApplication.run(HelpMeApplication.class, args);
		
		List<Integer> a = new ArrayList<Integer>();
		a.add(new Integer(1));
		a.add(new Integer(2));
		a.add(new Integer(3));
		a.add(new Integer(4));
		
		for (int i=0;i<a.size();i++)
		{
			Integer var = a.get(i);
			if(var == 2)
			{
				a.remove(var);
				continue;
			}
//			System.out.println(var);
		}
		
		for (int i=0;i<a.size();i++)
		{
			Integer var = a.get(i);
			System.out.println(var);
		}
		
	}

}
