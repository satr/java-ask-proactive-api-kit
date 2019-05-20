package com.github.satr.ask.components;

import com.github.satr.ask.proactive.api.ProactiveEventProvider;
import com.github.satr.ask.proactive.api.events.ProactiveEvent;

import java.util.ArrayList;
import java.util.List;

public class TestProactiveEventProvider implements ProactiveEventProvider {
    @Override
    public List<ProactiveEvent> getEvents() {
        return ObjectMother.getProactiveEvents(1, 10);
    }
}
