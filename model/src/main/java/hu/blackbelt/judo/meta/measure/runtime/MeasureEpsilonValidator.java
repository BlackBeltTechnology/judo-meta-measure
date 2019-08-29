package hu.blackbelt.judo.meta.measure.runtime;

import static hu.blackbelt.epsilon.runtime.execution.ExecutionContext.executionContextBuilder;
import static hu.blackbelt.epsilon.runtime.execution.contexts.EvlExecutionContext.evlExecutionContextBuilder;
import static hu.blackbelt.epsilon.runtime.execution.model.emf.WrappedEmfModelContext.wrappedEmfModelContextBuilder;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonMap;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.epsilon.common.util.UriUtil;

import hu.blackbelt.epsilon.runtime.execution.ExecutionContext;
import hu.blackbelt.epsilon.runtime.execution.api.Log;
import hu.blackbelt.epsilon.runtime.execution.exceptions.ScriptExecutionException;
import hu.blackbelt.judo.meta.measure.runtime.MeasureModel;

public class MeasureEpsilonValidator {
	
	public static void validateMeasure(Log log,
                                   MeasureModel measureModel,
                                   URI scriptRoot) throws ScriptExecutionException, URISyntaxException
	{
		validateMeasure(log, measureModel, scriptRoot, emptyList(), emptyList());
	}
	
	public static void validateMeasure(Log log,
                                   MeasureModel measureModel,
                                   URI scriptRoot,
                                   Collection<String> expectedErrors,
                                   Collection<String> expectedWarnings) throws ScriptExecutionException, URISyntaxException
	{
		ExecutionContext executionContext = executionContextBuilder()
	            .log(log)
	            .resourceSet(measureModel.getResourceSet())
	            .metaModels(emptyList())
	            .modelContexts(Arrays.asList(
	                    wrappedEmfModelContextBuilder()
	                            .log(log)
	                            .name("MEASURE")
	                            .validateModel(false)
	                            .resource(measureModel.getResource())
	                            .build()))
	            .injectContexts(singletonMap("measureUtils", new MeasureUtils()))
	            .build();
		
		 try {
	            // run the model / metadata loading
	            executionContext.load();

	            // Transformation script
	            executionContext.executeProgram(
	                    evlExecutionContextBuilder()
	                            .source(UriUtil.resolve("measure.evl", scriptRoot))
	                            .expectedErrors(expectedErrors)
	                            .expectedWarnings(expectedWarnings)
	                            .build());

	        } finally {
	            executionContext.commit();
	            try {
	                executionContext.close();
	            } catch (Exception e) {}
	        }
	}

}
