package de.cptahmad.anno.states;

public interface State
{
    void update(float delta);

    void render();

    void onEnter();

    void onExit();
}
