package dev.foodcans.cumulativexp.lang;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LangFile
{
    private File file;
    private FileConfiguration config;

    public LangFile(File parent, String path)
    {
        this.file = new File(parent, path);
        try
        {
            if (!this.file.exists())
            {
                //noinspection ResultOfMethodCallIgnored
                this.file.createNewFile();
            }
            this.config = YamlConfiguration.loadConfiguration(this.file);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        init();
    }

    private void init()
    {
        try
        {
            for (Lang lang : Lang.values())
            {
                if (!config.contains(lang.getPath()))
                    config.set(lang.getPath(), lang.getDefault());
            }

            config.save(this.file);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getValue(String path, String def)
    {
        return this.config.getString(path, def);
    }
}
