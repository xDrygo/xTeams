# 📖 Getting Started

### 1️⃣ **Installation**

To get started with **xTeams**, you first need to install the plugin on your Minecraft server. Follow these steps for proper installation:

* **Requirements**: You will need Spigot or Paper, as the plugin is not compatible with CraftBukkit.
* **Optional Dependencies**: **PlaceholderAPI** is an optional dependency that you can use to enhance the functionality of placeholders within **xTeams**.

Once the plugin is downloaded, simply place it in your server's `plugins` folder and restart or reload the server. You can check more details about installation in our installation section.

***

### 2️⃣ **Create and Manage Teams**

Now that you have **xTeams** installed, let's learn how to create and manage teams on your server.

#### **Create a Team**

To create a team in **xTeams**, use the following command:

```
/xteams create <team_name>
```

Replace `<team_name>` with the codename you want for the team. Example: `/xteams create alpha` will create a team with the code name: alpha

#### **Managing the Team**

*   **Join a Team**\
    To join a team, use the following command:

    ```
    /xteams join <team_name> <player_name>
    ```

    Example: `/xteams join alpha <your_name>` will add a player to the alpha team.
*   **Leave a Team**\
    To leave the team you are in, execute:

    ```
    /xteams leave <team_name> <player_name>
    ```

    Example: `/xteams leave alpha <your_name>` will make the player leave the "Alpha" team.
*   **View Team Information**\
    To get information about a team, use:

    ```
    /xteams teaminfo <team_name>
    ```

    Example: `/xteams teaminfo alpha` will give you details about the "Alpha" team, such as its members and status.

#### **Customize the Team Display**

Using the `/xteams setdisplay` command, you can change the text shown for a team in the team list.

```
/xteams setdisplay <team_name> "<display_text>"
```

Example: `/xteams setdisplay alpha "Welcome to the Alpha team!"` will change the display text for the "Alpha" team to "Welcome to the Alpha team!"

#### **Delete a Team**

If you decide to delete a team, you can do so with:

```
/xteams delete <team_name>
```

Example: `/xteams delete alpha` will delete the "Alpha" team and its data.

***

### 3️⃣ **More Resources**

For more information on other commands, permissions, and configurations, check out the following sections:

* **Information**
  * [Commands](../plugin/commands.md)
  * [Permissions](../plugin/permissions.md)
  * [Placeholders](../plugin/placeholders.md)
* **Configuration Files**
  * [config.yml](../configuration-files/config.yml.md)
  * [messages.yml](../configuration-files/messages.yml.md)

If you have questions or need further assistance, you can also check the Troubleshooting section.

***

Now you're ready to start creating and managing your teams in **xTeams**. If you need more help, feel free to continue exploring our wiki. 🚀

***
