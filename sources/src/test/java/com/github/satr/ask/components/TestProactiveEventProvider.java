package com.github.satr.ask.components;

import com.github.satr.ask.proactive.api.ProactiveEventProvider;
import com.github.satr.ask.proactive.api.events.ProactiveEvent;

public class TestProactiveEventProvider implements ProactiveEventProvider {
    @Override
    public ProactiveEvent getEvent() {
        return ObjectMother.getProactiveEvent(1,10);
    }
}
