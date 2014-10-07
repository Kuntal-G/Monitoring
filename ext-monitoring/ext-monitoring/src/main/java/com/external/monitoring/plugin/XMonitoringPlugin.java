package com.external.monitoring.plugin;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("plugin")
public class XMonitoringPlugin {

	@XStreamAsAttribute
	public String name;

	@XStreamAsAttribute
	public String clazz;

	@XStreamAsAttribute
	public String description;
	
	@XStreamImplicit
	public List<XArgument> args;

}
