package net.poundex.jtop.lanterna

import groovy.transform.CompileStatic
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(JtopLanternaContextConfiguration)
@CompileStatic
class JtopApplication
{
	static void main(String[] args)
	{
		new AnnotationConfigApplicationContext(JtopApplication)
	}

}
