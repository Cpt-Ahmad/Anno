package de.cptahmad.anno.entity;

import de.cptahmad.anno.main.LoadingException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class EntityCollection<T>
{
    protected final HashMap<String, T> s_entities = new HashMap<>();

    protected EntityCollection()
    {
    }

    @SuppressWarnings("unchecked")
    public void init(@NotNull Map<String, Object> loadedFile)
    {
        for (String key : loadedFile.keySet())
        {
            if (s_entities.containsKey(key))
                throw new LoadingException("duplicate key: " + key);

            T entity = loadPrototypeFromMap(key, (Map<String, Object>) loadedFile.get(key));
            s_entities.put(key, entity);
        }
    }

    protected abstract T loadPrototypeFromMap(String name, Map<String, Object> properties);

    public T get(String name)
    {
        if (!s_entities.containsKey(name))
            throw new IllegalArgumentException("there is no entity called " + name);

        return s_entities.get(name);
    }
}
