package com.github.satr.ask.proactive.api;
// Copyright © 2019, github.com/satr, MIT License

import com.github.satr.ask.proactive.api.events.ProactiveEvent;
import java.util.List;

public interface ProactiveEventProvider {
    List<ProactiveEvent> getEvents();
}
