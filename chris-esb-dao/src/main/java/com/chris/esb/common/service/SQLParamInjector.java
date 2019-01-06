package com.chris.esb.common.service;

import java.sql.PreparedStatement;

public interface SQLParamInjector {

    void injectSQLParam(PreparedStatement ps) throws Exception;
}
