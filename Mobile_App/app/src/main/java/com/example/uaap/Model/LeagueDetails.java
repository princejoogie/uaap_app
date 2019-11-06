package com.example.uaap.Model;

public class LeagueDetails {
    public String id;
    public String name;

    @Override
    public String toString() {
        return this.name;            // What to display in the Spinner list.
    }
}

