package nl.agiletech.flow.flw.matchers;

import nl.agiletech.flow.flw.http.DefaultResourceMatcher;
import nl.agiletech.flow.flw.http.ResourceMatcher;

public final class AllMatchers {
	public static final String PROP_APIMETHOD = "requesturi:apiMethod";
	public static final String PROP_PROJECTNAME = "requesturi:projectName";
	public static final String PROP_RESOURCENAME = "requesturi:resourceName";

	/**
	 * Matches: POST /inspect/{project}/*
	 * 
	 * @return
	 */
	public static ResourceMatcher createInspectMatcher() {
		DefaultResourceMatcher rm = new DefaultResourceMatcher();
		rm.setMatchMethod("post");
		rm.addLiteralSegment("inspect", PROP_APIMETHOD);
		rm.addSubstitutionSegment(PROP_PROJECTNAME);
		return rm;
	}

	/**
	 * Matches: POST /update/{project}/*
	 * 
	 * @return
	 */
	public static ResourceMatcher createUpdateMatcher() {
		DefaultResourceMatcher rm = new DefaultResourceMatcher();
		rm.setMatchMethod("post");
		rm.addLiteralSegment("update", PROP_APIMETHOD);
		rm.addSubstitutionSegment(PROP_PROJECTNAME);
		return rm;
	}

	/**
	 * Matches: POST /resource/{project}/{resourceName}/*
	 * 
	 * @return
	 */
	public static ResourceMatcher createResourceMatcher() {
		DefaultResourceMatcher rm = new DefaultResourceMatcher();
		rm.setMatchMethod("post");
		rm.addLiteralSegment("resource", PROP_APIMETHOD);
		rm.addSubstitutionSegment(PROP_PROJECTNAME);
		rm.addSubstitutionSegment(PROP_RESOURCENAME);
		return rm;
	}
}
