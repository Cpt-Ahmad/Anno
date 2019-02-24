package de.cptahmad.anno.eventsystem.eventlisteners;

import de.cptahmad.anno.entity.Entities;
import de.cptahmad.anno.entity.Mapper;
import de.cptahmad.anno.entity.buildings.Building;
import de.cptahmad.anno.entity.buildings.BuildingComparatorForRendering;
import de.cptahmad.anno.entity.buildings.PrototypeBuilding;
import de.cptahmad.anno.entity.buildings.presets.PRoad;
import de.cptahmad.anno.entity.components.RoadConnection;
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

    private final BuildingComparatorForRendering m_comparatorForRendering = new BuildingComparatorForRendering();

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

            if (event.building.type == PrototypeBuilding.Type.ROAD)
            {
                handleRoadBuildEvent(event.x, event.y);
            } else if (event.building.hasComponent(RoadConnection.class))
            {
                RoadConnection rc = event.building.getComponent(Mapper.ROAD_CONNECTION);
                handleRoadBuildEvent(event.x + rc.dx, event.y + rc.dy);
            }

            m_buildings.sort(m_comparatorForRendering);

            e.setHandled();
        }
    }

    private void handleRoadBuildEvent(int x, int y)
    {
        Point2DInteger road = new Point2DInteger(x, y);
        m_graph.addVertex(road);
        Point2DInteger above      = new Point2DInteger(x, y + 1);
        Point2DInteger below      = new Point2DInteger(x, y - 1);
        Point2DInteger leftPoint  = new Point2DInteger(x - 1, y);
        Point2DInteger rightPoint = new Point2DInteger(x + 1, y);

        boolean up    = m_graph.containsVertex(above);
        boolean down  = m_graph.containsVertex(below);
        boolean left  = m_graph.containsVertex(leftPoint);
        boolean right = m_graph.containsVertex(rightPoint);

        if (up)
        {
            m_graph.addEdge(road, above);

            boolean upup    = m_graph.containsVertex(new Point2DInteger(x, y + 2));
            boolean upleft  = m_graph.containsVertex(new Point2DInteger(x - 1, y + 1));
            boolean upright = m_graph.containsVertex(new Point2DInteger(x + 1, y + 1));

            Building building = m_buildings.stream().filter(b -> b.isAt(above.x, above.y)).findFirst().orElse(null);
            if (building != null && building.is(Entities.getBuilding("road_trail")))
            {
                ((PRoad) building.building).adjustRoadSprite(building.sprite, upup, true, upleft, upright);
            }
        }
        if (down)
        {
            m_graph.addEdge(road, below);

            boolean downdown  = m_graph.containsVertex(new Point2DInteger(x, y - 2));
            boolean downleft  = m_graph.containsVertex(new Point2DInteger(x - 1, y - 1));
            boolean downright = m_graph.containsVertex(new Point2DInteger(x + 1, y - 1));

            Building building = m_buildings.stream().filter(b -> b.isAt(below.x, below.y)).findFirst().orElse(null);
            if (building != null && building.is(Entities.getBuilding("road_trail")))
            {
                ((PRoad) building.building).adjustRoadSprite(building.sprite, true, downdown, downleft, downright);
            }
        }
        if (left)
        {
            m_graph.addEdge(road, leftPoint);

            boolean leftup   = m_graph.containsVertex(new Point2DInteger(x - 1, y + 1));
            boolean leftdown = m_graph.containsVertex(new Point2DInteger(x - 1, y - 1));
            boolean leftleft = m_graph.containsVertex(new Point2DInteger(x - 2, y));

            Building building =
                    m_buildings.stream().filter(b -> b.isAt(leftPoint.x, leftPoint.y)).findFirst().orElse(null);
            if (building != null && building.is(Entities.getBuilding("road_trail")))
            {
                ((PRoad) building.building).adjustRoadSprite(building.sprite, leftup, leftdown, leftleft, true);
            }
        }
        if (right)
        {
            m_graph.addEdge(road, rightPoint);

            boolean rightup    = m_graph.containsVertex(new Point2DInteger(x + 1, y + 1));
            boolean rightdown  = m_graph.containsVertex(new Point2DInteger(x + 1, y - 1));
            boolean rightright = m_graph.containsVertex(new Point2DInteger(x + 2, y));

            Building building = m_buildings.stream().filter(b -> b.isAt(rightPoint.x, rightPoint.y)).findFirst().orElse(null);
            if (building != null && building.is(Entities.getBuilding("road_trail")))
            {
                ((PRoad) building.building).adjustRoadSprite(building.sprite, rightup, rightdown, true, rightright);
            }
        }

        Building building =
                m_buildings.stream().filter(b -> b.isAt(x, y)).findFirst().orElse(null);
        if (building != null && building.is(Entities.getBuilding("road_trail")))
        {
            ((PRoad) building.building).adjustRoadSprite(building.sprite, up, down, left, right);
        }
    }
}
