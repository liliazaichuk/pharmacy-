package org.example;

public class LeafNode implements Node {
    private String name;
    private Runnable action;

    public LeafNode(String name, Runnable action) {
        this.name = name;
        this.action = action;
    }

    @Override
    public void execute() {
        action.run();
    }

    @Override
    public String getName() {
        return name;
    }
}
