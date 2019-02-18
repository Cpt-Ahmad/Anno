package de.cptahmad.anno.eventsystem.eventlisteners;

import de.cptahmad.anno.entity.Entities;
import de.cptahmad.anno.entity.buildings.Building;
import de.cptahmad.anno.entity.buildings.presets.PRoad;
import de.cptahmad.anno.eventsystem.Event;
import de.cptahmad.anno.eventsystem.EventListener;
import de.cptahmad.anno.eventsystem.events.BuildingConstructedEvent;
import de.cptahmad.anno.util.Point2DInteger;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;

public class BuildingConstructedListener extends EventListener
{
    private final Graph<Point2DInteger, DefaultEdge> m_graph;
    private final List<Building>                     m_buildings;

    public BuildingConstructedListener(
            Graph<Point2DInteger, DefaultEdge> graph,
            List<Building> buildings)
    {

        this.m_graph = graph;
        this.m_buildings = buildings;
    }

    /**
     * Processes the event in case it actually can process it.
     *
     * @param e The event that should be processed
     */
    @Override
    public void processEvent(Event e)
    {
        if (e instanceof BuildingConstructedEvent)
        {
            BuildingConstructedEvent event = (BuildingConstructedEvent) e;
            switch(event.building.type)
            {
                case ROAD:
                    handleRoadBuildEvent(event);
                    break;
            }
        }
    }

    private void handleRoadBuildEvent(BuildingConstructedEvent e)
    {
        Point2DInteger road = new Point2DInteger(e.x, e.y);
        m_graph.addVertex(road);
        Point2DInteger above      = new Point2DInteger(e.x, e.y + 1);
        Point2DInteger below      = new Point2DInteger(e.x, e.y - 1);
        Point2DInteger leftPoint  = new Point2DInteger(e.x - 1, e.y);
        Point2DInteger rightPoint = new Point2DInteger(e.x + 1, e.y);

        boolean up    = m_graph.containsVertex(above);
        boolean down  = m_graph.containsVertex(below);
        boolean left  = m_graph.containsVertex(leftPoint);
        boolean right = m_graph.containsVertex(rightPoint);

        if (up)
        {
            m_graph.addEdge(road, above);
            Building building = m_buildings.stream().filter(b -> b.isAt(above.x, above.y)).findFirst().orElse(null);
            if (building == null || !building.is(Entities.getBuilding("road_trail")))
                throw new IllegalStateException("the road graph and the actual road buildings do not match");

            boolean upup    = m_graph.containsVertex(new Point2DInteger(e.x, e.y + 2));
            boolean upleft  = m_graph.containsVertex(new Point2DInteger(e.x - 1, e.y + 1));
            boolean upright = m_graph.containsVertex(new Point2DInteger(e.x + 1, e.y + 1));

            ((PRoad) building.building).adjustRoadSprite(building.sprite, upup, true, upleft, upright);
        }
        if (down)
        {
            m_graph.addEdge(road, below);
            Building building = m_buildings.stream().filter(b -> b.isAt(below.x, below.y)).findFirst().orElse(null);
            if (building == null || !building.is(Entities.getBuilding("road_trail")))
                throw new IllegalStateException("the road graph and the actual road buildings do not match");

            boolean downdown  = m_graph.containsVertex(new Point2DInteger(e.x, e.y - 2));
            boolean downleft  = m_graph.containsVertex(new Point2DInteger(e.x - 1, e.y - 1));
            boolean downright = m_graph.containsVertex(new Point2DInteger(e.x + 1, e.y - 1));

            ((PRoad) building.building).adjustRoadSprite(building.sprite, true, downdown, downleft, downright);
        }
        if (left)
        {
            m_graph.addEdge(road, leftPoint);
            Building building =
                    m_buildings.stream().filter(b -> b.isAt(leftPoint.x, leftPoint.y)).findFirst().orElse(null);
            if (building == null || !building.is(Entities.getBuilding("road_trail")))
                throw new IllegalStateException("the road graph and the actual road buildings do not match");

            boolean leftup   = m_graph.containsVertex(new Point2DInteger(e.x - 1, e.y + 1));
            boolean leftdown = m_graph.containsVertex(new Point2DInteger(e.x - 1, e.y - 1));
            boolean leftleft = m_graph.containsVertex(new Point2DInteger(e.x - 2, e.y));

            ((PRoad) building.building).adjustRoadSprite(building.sprite, leftup, leftdown, leftleft, true);
        }
        if (right)
        {
            m_graph.addEdge(road, rightPoint);
            Building building =
                    m_buildings.stream().filter(b -> b.isAt(rightPoint.x, rightPoint.y)).findFirst().orElse(null);
            if (building == null || !building.is(Entities.getBuilding("road_trail")))
                throw new IllegalStateException("the road graph and the actual road buildings do not match");

            boolean rightup    = m_graph.containsVertex(new Point2DInteger(e.x + 1, e.y + 1));
            boolean rightdown  = m_graph.containsVertex(new Point2DInteger(e.x + 1, e.y - 1));
            boolean rightright = m_graph.containsVertex(new Point2DInteger(e.x + 2, e.y));

            ((PRoad) building.building).adjustRoadSprite(building.sprite, rightup, rightdown, true, rightright);
        }

        Building building =
                m_buildings.stream().filter(b -> b.isAt(e.x, e.y)).findFirst().orElse(null);
        if (building == null || !building.is(Entities.getBuilding("road_trail")))
            throw new IllegalStateException("the road graph and the actual road buildings do not match");
        ((PRoad) building.building).adjustRoadSprite(building.sprite, up, down, left, right);

        e.setHandled();
    }
}
