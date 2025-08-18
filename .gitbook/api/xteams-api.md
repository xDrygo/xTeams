# 🛡️ xTeams API

## 💻 xTeams API

The **xTeamsAPI** provides static methods to interact programmatically with **xTeams**. You can manage teams, their members, priorities, and check team membership directly from your code without creating an instance.

***

### 🛠 Installation

Add the repository and dependency for **xTeams**.

{% tabs %}
{% tab title="Maven" %}
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.xDrygo</groupId>
    <artifactId>xTeams</artifactId>
    <version>1.3.1</version>
    <scope>provided</scope>
</dependency>
```
{% endtab %}

{% tab title="Gradle (Kotlin DSL)" %}
```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

dependencies {
    implementation("com.github.xDrygo:xTeams:1.3.1")
}
```
{% endtab %}

{% tab title="Gradle (Groovy DSL)" %}
```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.xDrygo:xTeams:1.3.1'
}
```
{% endtab %}
{% endtabs %}

After adding the dependency, add xTeams as dependency in your plugin.

{% tabs %}
{% tab title="plugin.yml" %}
```yaml
name: MyPlugin
main: com.example.MyPlugin.MyPlugin
version: 1.0
depend: # depend if API is required, soft-depend if optional
- xTeams
```
{% endtab %}
{% endtabs %}

***

### 📋 XTeamsAPI Methods

<details>

<summary>Click to expand</summary>

* **`Team getTeam(String name)`**\
  Returns a team by its name.

```java
Team myTeam = XTeamsAPI.getTeam("Red");
```

* **`Set<Team> getAllTeams()`**\
  Returns all existing teams.

```java
Set<Team> teams = XTeamsAPI.getAllTeams();
```

* **`void createTeam(String name, String displayName, int priority, Set<String> members)`**\
  Creates a new team.

```java
XTeamsAPI.createTeam("Red", "Red Team", 1, new HashSet<>(Arrays.asList("Steve", "Alex")));
```

* **`void deleteTeam(Team team)`**\
  Deletes a specific team.

```java
XTeamsAPI.deleteTeam(myTeam);
```

* **`Map<String, Object> getTeamInfo(Team team)`**\
  Returns information about a team.

```java
Map<String, Object> info = XTeamsAPI.getTeamInfo(myTeam);
```

* **`void joinTeam(String player, Team team)`**\
  Adds a player to a team.

```java
XTeamsAPI.joinTeam("Steve", myTeam);
```

* **`void leaveTeam(String player, Team team)`**\
  Removes a player from a team.

```java
XTeamsAPI.leaveTeam("Steve", myTeam);
```

* **`void joinAllTeams(String player)`**\
  Adds a player to all teams.

```java
XTeamsAPI.joinAllTeams("Alex");
```

* **`void leaveAllTeams(String player)`**\
  Removes a player from all teams.

```java
XTeamsAPI.leaveAllTeams("Alex");
```

* **`boolean isInTeam(String player, Team team)`**\
  Checks if a player is in a specific team.

```java
boolean inTeam = XTeamsAPI.isInTeam("Steve", myTeam);
```

* **`boolean isInAnyTeam(String player)`**\
  Checks if a player is in any team.

```java
boolean inAnyTeam = XTeamsAPI.isInAnyTeam("Steve");
```

* **`List<Team> getPlayerTeams(String player)`**\
  Returns a list of teams the player belongs to.

```java
List<Team> playerTeams = XTeamsAPI.getPlayerTeams("Steve");
```

* **`Team getPlayerTeam(String player)`**\
  Returns the primary team of a player.

```java
Team primaryTeam = XTeamsAPI.getPlayerTeam("Steve");
```

* **`List<String> listTeamNames()`**\
  Returns a list of all team names.

```java
List<String> teamNames = XTeamsAPI.listTeamNames();
```

</details>

### 🎉 XTeams Events

<details>

<summary>Click to expand</summary>

**Events:**

* **TeamCreateEvent** - Fired when a team is created.
* **TeamDeleteEvent** - Fired when a team is deleted.
* **TeamJoinEvent** - Fired when a player joins a team.
* **TeamLeaveEvent** - Fired when a player leaves a team.

Each event extends `org.bukkit.event.Event` and can be listened to using normal Bukkit event listeners.

```java
@EventHandler
public void onTeamCreate(TeamCreateEvent event) {
    Bukkit.getLogger().info("Team created: " + event.getTeam().getName());
}
```

</details>

***

### 💡 Notes

* All API methods are **static**, so you do **not** need to instantiate `XTeamsAPI`.
* Team methods handle creation, deletion, membership, and priority automatically.
* Events allow you to react to team changes in real time.

***

### 🔗 References

* [xTeams GitHub Repository](https://github.com/xDrygo/xTeams)
* [Wiki Home](https://github.com/xDrygo/xTeams/wiki)
