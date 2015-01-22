package org.salephoto.salephotographicsociety.events;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


abstract public class AbstractEvent {
    private Set<String> fields = new HashSet<>();

    public AbstractEvent() {
    }

    public AbstractEvent(final Set<String> fields) {
        this.fields = fields;
    }

    public Set<String> getFields() {
        return fields;
    }

    public String getFieldQuery() {
        final StringBuilder query = new StringBuilder();
        for (String field : fields) {
            if (query.length() > 0) {
                query.append(',');
            }
            query.append(field);
        }

        return query.toString();
    }

    public void add(final String field) {
        fields.add(field);
    }

    public void addAll(final Collection<String> fields) {
        fields.addAll(fields);
    }
}
