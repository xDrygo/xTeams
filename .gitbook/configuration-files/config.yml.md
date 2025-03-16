# 📝 config.yml

## 📄 **Introduction**

The <kbd>config.yml</kbd> file is the core of the **xTeams** plugin configuration. Here, you can define and manage teams, their members, and priorities. This file allows you to customize the team settings to fit your server’s needs, such as the team’s name, the members it includes, and the priority with which they should be treated within the plugin.

## **💡 Breakdown of the&#x20;**<kbd>**config.yml**</kbd>**&#x20;file:**

```yaml
teams:
  red:
    displayName: "The Red Team"
    members:
      - player1
      - player2
    priority: 1

  blue:
    displayName: "The Blue Team""
    members:
      - player3
      - player4
    priority: 2
```

* <kbd>**teams**</kbd>: This section defines all the teams in your plugin. Each team is identified by a unique ID (e.g., `red`, `blue`), and you can customize the name, members, and priority for each one.
  * <kbd>**red**</kbd>: This is the unique ID of the first team.
    * <kbd>**displayName**</kbd>: Defines the team’s display name (e.g., "The Red Team").
    * <kbd>**members**</kbd>: A list of players belonging to this team.
    * <kbd>**priority**</kbd>: A numeric value that defines the team’s priority. A higher number indicates higher priority.
