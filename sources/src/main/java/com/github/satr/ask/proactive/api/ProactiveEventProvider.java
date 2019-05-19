package com.github.satr.ask.proactive.api;
// Copyright © 2019, github.com/satr, MIT License

import com.github.satr.ask.proactive.api.events.ProactiveEvent;

public interface ProactiveEventProvider {
    ProactiveEvent getEvent();
}
