package org.mule.connectors.commons.template.service;

import org.mule.connectors.commons.template.config.ConnectorConfig;
import org.mule.connectors.commons.template.connection.ConnectorConnection;

public interface ConnectorService <CONFIG extends ConnectorConfig, CONNECTION extends ConnectorConnection> {

}
