package com.yorksale.actalyzer.model;

import org.neo4j.graphdb.RelationshipType;

/**
 * Created by Yashar HN
 * Date: 05/08/15 3:41 PM
 */
public enum RelTypes implements RelationshipType {
    VISITED,
    CLICKED,
    TAGGED,
    LOCATED,

}
