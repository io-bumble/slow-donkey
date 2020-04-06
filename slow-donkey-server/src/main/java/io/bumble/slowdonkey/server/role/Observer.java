package io.bumble.slowdonkey.server.role;

import io.bumble.slowdonkey.common.model.network.base.Request;
import io.bumble.slowdonkey.common.model.network.base.Response;
import io.bumble.slowdonkey.server.LifeCycle;

/**
 * @author shenxiangyu on 2020/04/04
 */
public class Observer extends AbstractLearner implements LifeCycle {

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    protected <T extends Request, R extends Response> R doReceiveRequest(T request) {
        return null;
    }
}
