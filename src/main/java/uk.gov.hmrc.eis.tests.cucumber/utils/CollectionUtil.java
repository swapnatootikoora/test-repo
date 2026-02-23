package uk.gov.hmrc.eis.tests.cucumber.utils;

import java.util.AbstractMap.SimpleEntry;
import java.uil.Collections;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class CollectionUtil {
    public static <K, V> Map<K, List<V>> toMultiValueMap(Map<K, V> singleValueMap) {
        return singleValueMap
                .entrySet()
                .stream()
                .map(e -> new SimpleEntry<>(e.getKey(), Collections.singletonList((e.getValue())))
                        .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));

    }
    private CollectionUtil() {}

}