package de.cptahmad.anno.states;

import java.util.Stack;

public class StateStacker
{
    private final Stack<State> m_states = new Stack<State>();

    public void update(float delta)
    {
        if(!m_states.empty()) m_states.peek().update(delta);
    }

    public void render()
    {
        if(!m_states.empty()) m_states.peek().render();
    }

    public void pop()
    {
        if(!m_states.empty()) m_states.pop().onExit();
        if(!m_states.empty()) m_states.peek().onEnter();
    }

    public void push(State state)
    {
        if(!m_states.empty()) m_states.peek().onExit();
        m_states.push(state);
        state.onEnter();
    }

    public boolean isEmpty()
    {
        return m_states.empty();
    }

    public void closeAll()
    {
        while(!m_states.empty())
        {
            pop();
        }
    }
}
