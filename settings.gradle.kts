val name = "sickcore"
rootProject.name = name

include("$name-survival")
include(":$name-api:fabric")
findProject(":$name-api:fabric")?.name = "$name-api-fabric"