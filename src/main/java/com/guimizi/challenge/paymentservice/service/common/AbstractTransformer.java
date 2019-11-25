package com.guimizi.challenge.paymentservice.service.common;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractTransformer<I, O> {

    public abstract O transform(I input);

    public List<O> transformAll(Collection<I> inputs) {
        return inputs.stream().map(this::transform).collect(Collectors.toList());
    }

}
