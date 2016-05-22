package so.pickme.utils.config;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.isdc.wro.config.jmx.ConfigConstants;
import ro.isdc.wro.config.jmx.WroConfiguration;
import ro.isdc.wro.http.ConfigurableWroFilter;
import ro.isdc.wro.manager.factory.standalone.DefaultStandaloneContextAwareManagerFactory;
import ro.isdc.wro.manager.factory.standalone.StandaloneContext;
import ro.isdc.wro.model.WroModel;
import ro.isdc.wro.model.factory.WroModelFactory;
import ro.isdc.wro.model.group.Group;
import ro.isdc.wro.model.group.Inject;
import ro.isdc.wro.config.Context;
import ro.isdc.wro.model.resource.Resource;
import ro.isdc.wro.model.resource.locator.factory.UriLocatorFactory;
import ro.isdc.wro.model.resource.processor.factory.ConfigurableProcessorsFactory;
//import ro.isdc.wro.maven.plugin.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.model.resource.processor.factory.ProcessorsFactory;

@Configuration
public class Wro4jConfiguration {
	 Logger logger = LoggerFactory.getLogger(Wro4jConfiguration.class);
	
	  @Inject
	  private WroModelFactory modelFactory;
	  @Inject
	  private WroConfiguration config;
	  @Inject
	  private UriLocatorFactory locatorFactory;
	  private static final String GROUP_NAME = "g1";
	  private static final String RESOURCE_URI = "/test.js";
	  
	  final WroModel model = new WroModel().addGroup(new Group(GROUP_NAME).addResource(Resource.create(RESOURCE_URI)));
	  
	  
	  private StandaloneContext createStandaloneContext() throws URISyntaxException {
	        Context.set(Context.standaloneContext());
	        final StandaloneContext runContext = new StandaloneContext();
	        Path currentDirectory = Paths.get("").toAbsolutePath();
			Path finalpath = Paths.get(currentDirectory.toAbsolutePath()+"\\src\\main\\wro\\");
	        //final String name =  "wro.xml";
	        //final String normalizedName = FilenameUtils.normalize(finalpath, true); 
	        runContext.setWroFile(finalpath.resolve("wro.xml").toFile());
	        logger.debug("WROX contect path"+finalpath.toAbsolutePath().toString());
	        runContext.setContextFoldersAsCSV(currentDirectory.toString());
	        return runContext; 
	    }
	  
	  private DefaultStandaloneContextAwareManagerFactory getManagerFactory() throws URISyntaxException {
	        DefaultStandaloneContextAwareManagerFactory dcsaf = new DefaultStandaloneContextAwareManagerFactory() {
	        
	            @Override
	            protected ProcessorsFactory newProcessorsFactory() { 
	              return  new ConfigurableProcessorsFactory(){
	            	 
	              }.setProperties(createProperties());  
	            }
	            protected Properties createProperties() {
	            	Properties properties = new Properties();
	                properties.put("preProcessors", "lessCssImport");
	                properties.put("postProcessors", "less4j,jsMin");
	                properties.put("uriLocators", "servletContext,uri,classpath,webjar");
	                properties.put("cssDestinationFolder", "${project.build.directory}/generated-resources/static/css");
	                properties.put("jsDestinationFolder", "${project.build.directory}/generated-resources/static/js");
	                return properties;
	              }
	        } ;
	      
	        dcsaf.initialize(createStandaloneContext());
	        return dcsaf;
	    }
	  
	
	@Bean
	ConfigurableWroFilter wroFilter() throws URISyntaxException{
		ConfigurableWroFilter wroFilter = new ConfigurableWroFilter();
		wroFilter.setConfiguration(new WroConfiguration());
		wroFilter.setWroManagerFactory(getManagerFactory());
		wroFilter.setEnable(true);
		wroFilter.setDebug(true);
		return wroFilter;
	}

	
	@Bean
	Properties wro4jProperties() {
		Properties properties = new Properties();
		properties.put(ConfigConstants.debug, true);
		properties.put(ConfigConstants.cacheGzippedContent, true);
		properties.put(ConfigConstants.disableCache, false);
		properties.put(ConfigConstants.resourceWatcherUpdatePeriod, 1);
        properties.put(ConfigConstants.ignoreFailingProcessor, String.valueOf("true"));
        properties.put(ConfigConstants.ignoreMissingResources, String.valueOf("true"));
		return properties;
	}
	
	@Bean
	FilterRegistrationBean wro4jFilterRegister(ConfigurableWroFilter wroFilter )
	{
		FilterRegistrationBean filterRegBean = new FilterRegistrationBean(wroFilter);
		filterRegBean.addUrlPatterns("/wro/*");
		return filterRegBean;
	}
	
}