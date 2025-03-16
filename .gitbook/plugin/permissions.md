# 🔒 Permissions

## 📄 **Introduction**

Permissions are essential to control who can access specific features and commands in the **xTeams** plugin. By managing permissions, you can ensure that only authorized players can create, delete, or modify teams. Below is a table listing all the permissions available in **xTeams**, their examples, and a brief description of what they allow.

## **🔒 Plugin Permissions**

| 🔒 Permission                 | 📄 Description                                                                                                                                                                  |
| ----------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **xteams.command.help**       | Grants access to the <kbd>/xteams help</kbd> command. Players with this permission can view all available commands.                                                             |
| **xteams.command.reload**     | Allows access to the <kbd>/xteams reload</kbd> command. Players with this permission can reload the plugin’s settings.                                                          |
| **xteams.command.info**       | Grants access to the <kbd>/xteams info</kbd> command. Allows the player to view plugin details.                                                                                 |
| **xteams.command.create**     | Grants permission to create new teams with the <kbd>/xteams create</kbd> command. Only admins or trusted users should have this permission.                                     |
| **xteams.command.delete**     | Allows the player to delete teams using the <kbd>/xteams delete</kbd> command. Be careful, as this will permanently remove teams.                                               |
| x**teams.command.delete.all** | Allows the player to delete all teams by using <kbd>/xteams delete \*</kbd>. Be careful, if you grant this permission to the wrong player, it can delete all teams at one time. |
| **xteams.command.join**       | Grants the ability to add players to teams using the <kbd>/xteams join</kbd> command. Useful for admins or team leaders.                                                        |
| **xteams.command.leave**      | Allows players to leave teams using the <kbd>/xteams leave</kbd> command. Players must have permission to leave their own team.                                                 |
| **xteams.command.list**       | Grants access to the <kbd>/xteams list</kbd> command. Allows players to see a list of all active teams on the server.                                                           |
| **xteams.command.teaminfo**   | Grants access to the <kbd>/xteams teaminfo</kbd> command. Allows players to view detailed information about a specific team.                                                    |
| **xteams.command.playerinfo** | Allows players to see detailed information about other players with the <kbd>/xteams playerinfo</kbd> command.                                                                  |
| **xteams.command.setdisplay** | Grants the ability to set custom display text for teams using the <kbd>/xteams setdisplay</kbd> command. Only admins should have this permission.                               |
| **xteams.admin**              | Full admin rights for all **xTeams** commands and features. Gives the player complete control over the plugin.                                                                  |

***

## 📜 **Explanation**

* **Command Permissions**: Each of the listed commands has an associated permission that controls who can use them. For example, if you want a player to be able to create teams, they need the `xteams.command.create` permission. Similarly, if you want them to be able to delete teams, they need the `xteams.command.delete` permission.
* **Dangerous Permissions:** Some commands include arguments that allow users to manage all players or teams, which can be risky. These permissions should be handled with caution, as they give players the ability to make significant changes that can impact the entire server. For example, the permission `xteams.command.delete.all` enables players to delete all teams with a single command, which could lead to unintended consequences if misused. Always ensure that these permissions are only granted to trusted users or admins.
* **Admin Permission**: The `xteams.admin` permission provides full access to all plugin features and commands. This permission should be reserved for server administrators to prevent unauthorized changes.
