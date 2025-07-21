---
hidden: true
layout:
  title:
    visible: true
  description:
    visible: true
  tableOfContents:
    visible: true
  outline:
    visible: true
  pagination:
    visible: true
---

# 📚 xTeams API

## 📄 Introduction

Welcome to the documentation for <kbd>**xTeamsAPI**</kbd>, an API designed to interact with the team system of the **xTeams** plugin. This API allows you to manage teams and players easily, delegating most tasks to the plugin's <kbd>**TeamManager**</kbd>. Here you will find a complete guide on how to implement it, along with a breakdown of each of its methods.

## 🚀 Setup

### 📋 Requirements

To use **xTeamsAPI**, you need to have the **xTeams** plugin installed on your Minecraft server. This API is compatible with Minecraft versions that use Bukkit/Spigot.

### ⚙️ How to Implement <kbd>xTeamsAPI</kbd>

To start using the API, you first need to initialize it in your plugin class:

```java
javaCopyEditXTeamsAPI xTeamsAPI = new XTeamsAPI(plugin);
```

Where **plugin** is an instance of your main plugin class extending **JavaPlugin**.

## 📦 Content

### 🔧 Methods of <kbd>xTeamsAPI</kbd>

Below is a detailed explanation of each method provided by **xTeamsAPI**, along with examples of how to use them.

<details>

<summary><kbd>getPlayerTeamName(OfflinePlayer player)</kbd></summary>

**Description**: Gets the name of the team that a player belongs to.

**Parameters**:

* `player` - The player whose team name you want to retrieve.

**Return Value**:

* A `String` representing the team name of the player.

**Example**:

```java
javaCopyEditString teamName = xTeamsAPI.getPlayerTeamName(player);
```

</details>

<details>

<summary><kbd>setDisplayName(String teamName, String newDisplayName)</kbd></summary>

**Description**: Changes the visible name of a team.

**Parameters**:

* `teamName` - The name of the team.
* `newDisplayName` - The new display name you want to assign to the team.

**Return Value**:

* `true` if the operation was successful, `false` otherwise.

**Example**:

```java
javaCopyEditboolean success = xTeamsAPI.setDisplayName("MyTeam", "New Display Name");
```

</details>

<details>

<summary><kbd>getPlayerTeamDisplayName(OfflinePlayer player)</kbd></summary>

**Description**: Gets the visible name of the team that a player belongs to.

**Parameters**:

* `player` - The player whose team's display name you want to retrieve.

**Return Value**:

* A `String` representing the visible name of the player's team.

**Example**:

```java
javaCopyEditString displayName = xTeamsAPI.getPlayerTeamDisplayName(player);
```

</details>

<details>

<summary><kbd>getPlayerTeams(OfflinePlayer player)</kbd></summary>

**Description**: Gets all the teams a player belongs to.

**Parameters**:

* `player` - The player whose teams you want to retrieve.

**Return Value**:

* A list of `Team` objects representing the teams the player is part of.

**Example**:

```java
javaCopyEditList<Team> teams = xTeamsAPI.getPlayerTeams(player);
```

</details>

<details>

<summary><kbd>isPlayerInTeam(OfflinePlayer player, String teamName)</kbd></summary>

**Description**: Checks if a player is in a specific team.

**Parameters**:

* `player` - The player you want to check.
* `teamName` - The name of the team you want to check.

**Return Value**:

* `true` if the player is in the team, `false` otherwise.

**Example**:

```java
javaCopyEditboolean isInTeam = xTeamsAPI.isPlayerInTeam(player, "MyTeam");
```

</details>

<details>

<summary><kbd>getTeamMembers(String teamName)</kbd></summary>

**Description**: Gets all the members of a team.

**Parameters**:

* `teamName` - The name of the team.

**Return Value**:

* A set of strings (`Set<String>`) representing the player names of the team's members.

**Example**:

```java
javaCopyEditSet<String> members = xTeamsAPI.getTeamMembers("MyTeam");
```

</details>

<details>

<summary><kbd>listTeams()</kbd></summary>

**Description**: Retrieves a list of all the teams in the system.

**Return Value**:

* A list of strings (`List<String>`) with the names of all teams.

**Example**:

```java
javaCopyEditList<String> teams = xTeamsAPI.listTeams();
```

</details>

<details>

<summary><kbd>getTeamByName(String teamName)</kbd></summary>

**Description**: Gets a `Team` object that contains all the information about a team.

**Parameters**:

* `teamName` - The name of the team you want to retrieve.

**Return Value**:

* A `Team` object with the information about the team.

**Example**:

```java
javaCopyEditTeam team = xTeamsAPI.getTeamByName("MyTeam");
```

</details>

<details>

<summary><kbd>createTeam(String teamName, String displayName, int priority, Set&#x3C;String> members)</kbd></summary>

**Description**: Creates a new team.

**Parameters**:

* `teamName` - The name of the new team.
* `displayName` - The visible name of the team.
* `priority` - The priority of the team.
* `members` - A set of players who will be members of the team.

**Return Value**:

* This method does not return anything.

**Example**:

```java
javaCopyEditSet<String> members = new HashSet<>();
members.add("player1");
members.add("player2");
xTeamsAPI.createTeam("MyTeam", "Awesome Team", 1, members);
```

</details>

<details>

<summary><kbd>deleteTeam(String teamName)</kbd></summary>

**Description**: Deletes a team from the system.

**Parameters**:

* `teamName` - The name of the team you want to delete.

**Return Value**:

* This method does not return anything.

**Example**:

```java
javaCopyEditxTeamsAPI.deleteTeam("MyTeam");
```

</details>

<details>

<summary><kbd>joinTeam(OfflinePlayer player, String teamName)</kbd></summary>

**Description**: Allows a player to join a team.

**Parameters**:

* `player` - The player who will join the team.
* `teamName` - The name of the team the player will join.

**Return Value**:

* This method does not return anything.

**Example**:

```java
javaCopyEditxTeamsAPI.joinTeam(player, "MyTeam");
```

</details>

<details>

<summary><kbd>leaveTeam(OfflinePlayer player, String teamName)</kbd></summary>

**Description**: Allows a player to leave a team.

**Parameters**:

* `player` - The player who will leave the team.
* `teamName` - The name of the team the player will leave.

**Return Value**:

* This method does not return anything.

**Example**:

```java
javaCopyEditxTeamsAPI.leaveTeam(player, "MyTeam");
```

</details>

<details>

<summary><kbd>deleteAllTeams()</kbd></summary>

**escription**: Deletes all teams from the system.

**Return Value**:

* This method does not return anything.

**Example**:

```java
javaCopyEditxTeamsAPI.deleteAllTeams();
```

</details>
