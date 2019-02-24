package de.cptahmad.anno.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.TimeUtils;
import de.cptahmad.anno.entity.Entities;
import de.cptahmad.anno.entity.buildings.Building;
import de.cptahmad.anno.entity.items.Inventory;
import de.cptahmad.anno.entity.npcs.Npc;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public final class Data
{
    public final ArrayList<Building> buildings = new ArrayList<>();
    public final ArrayList<Npc>      npcs      = new ArrayList<>();

    public final Inventory inventory;

    public Data()
    {
        inventory = new Inventory();
    }

    public void save(String filename)
    {
        Yaml   yaml     = new Yaml();
        String filepath = "saves/" + filename;

        long before = TimeUtils.millis();

        FileHandle zipFile = Gdx.files.local(filepath + ".zip");

        try (ZipOutputStream zipStream = new ZipOutputStream(zipFile.write(false)))
        {
            zipStream.putNextEntry(new ZipEntry("buildings.yaml"));
            zipStream.write(saveBuildings(yaml).getBytes());
            zipStream.closeEntry();

            zipStream.putNextEntry(new ZipEntry("npcs.yaml"));
            zipStream.write(saveNpcs(yaml).getBytes());
            zipStream.closeEntry();

            zipStream.putNextEntry(new ZipEntry("inventory.yaml"));
            zipStream.write(saveInventory(yaml).getBytes());
            zipStream.closeEntry();

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        Gdx.app.debug("Data", "Done saving game files: " + TimeUtils.timeSinceMillis(before) + "ms");
    }

    private String saveInventory(Yaml yaml)
    {
        return yaml.dump(inventory.getInventorySaveData());
    }

    private String saveBuildings(Yaml yaml)
    {
        Map<String, List<Map<String, Object>>> buildingData = new HashMap<>();
        for (Building building : buildings)
        {
            if (!buildingData.containsKey(building.building.name))
            {
                buildingData.put(building.building.name, new ArrayList<>());
            }

            Map<String, Object> buildingSaveData = new HashMap<>();
            building.getSaveData(buildingSaveData);
            buildingData.get(building.building.name).add(buildingSaveData);
        }

        return yaml.dump(buildingData);
    }

    private String saveNpcs(Yaml yaml)
    {
        Map<String, Map<String, Object>> npcData = new HashMap<>();
        for (Npc npc : npcs)
        {
            Map<String, Object> npcSaveData = new HashMap<>();
            npc.getSaveData(npcSaveData);
            npcData.put(npc.id.toString(), npcSaveData);
        }

        return yaml.dump(npcData);
    }

    public void load(String filename)
    {
        Yaml   yaml     = new Yaml();
        String filepath = "saves/" + filename;

        long before = TimeUtils.millis();

        FileHandle zipFile = Gdx.files.local(filepath + ".zip");
        if (!zipFile.exists()) return; // TODO give some sort of feedback

        // TODO check whether or not the files inside the zip exist and give out an error message if not
        try (ZipFile zip = new ZipFile(zipFile.file()))
        {
            InputStream inputStream = zip.getInputStream(zip.getEntry("inventory.yaml"));
            loadInventory(yaml, inputStream);
            inputStream.close();

            inputStream = zip.getInputStream(zip.getEntry("npcs.yaml"));
            loadNpcs(yaml, inputStream);
            inputStream.close();

            inputStream = zip.getInputStream(zip.getEntry("buildings.yaml"));
            loadBuildings(yaml, inputStream);
            inputStream.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        Gdx.app.debug("Loading", "Savefile loading time: " + TimeUtils.timeSinceMillis(before) + "ms");
    }

    private void loadInventory(Yaml yaml, InputStream inputStream)
    {
        Map<String, Integer> invData = yaml.load(inputStream);

        if (invData == null) return;
        for (String key : invData.keySet())
        {
            int amount = invData.get(key);
            inventory.add(Entities.getItem(key), amount);
        }
    }

    private void loadBuildings(Yaml yaml, InputStream inputStream)
    {
        Map<String, List<Map<String, Object>>> buildingData = yaml.load(inputStream);

        for (String key : buildingData.keySet())
        {
            List<Map<String, Object>> listBuildingData = buildingData.get(key);
            for (Map<String, Object> singleBuildingData : listBuildingData)
            {
                Building building = new Building(Entities.getBuilding(key), singleBuildingData);
                buildings.add(building);
            }
        }
    }

    private void loadNpcs(Yaml yaml, InputStream inputStream)
    {
        Map<String, Map<String, Object>> npcData = yaml.load(inputStream);

        for (String key : npcData.keySet())
        {
            Map<String, Object> singleNpcData = npcData.get(key);
            UUID                npcId         = UUID.fromString(key);

            npcs.add(new Npc(npcId, singleNpcData));
        }
    }

    public String[] getAvailableProfiles()
    {
        FileHandle[] saveFiles = Gdx.files.local("saves/").list(".zip");
        String[] profiles = new String[saveFiles.length];

        for (int i = 0; i < saveFiles.length; i++)
        {
            profiles[i]  = saveFiles[i].nameWithoutExtension();
        }

        return profiles;
    }

    public void clear()
    {
        buildings.clear();
        npcs.clear();
        inventory.clear();
    }
}
