package com.external.monitoring.plugin;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("plugins")
public class XPlugin {

	@XStreamImplicit
	public List<XMonitoringPlugin> plugins;

}
