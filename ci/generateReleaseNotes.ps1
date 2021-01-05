[CmdletBinding()]
param()

$AzureDevOpsAuthenicationHeader = @{Authorization = "Bearer $env:SYSTEM_ACCESSTOKEN" }

$CurrentIterApiCall = "https://dev.azure.com/nepumuk/Notes/Notes%20Team/_apis/work/teamsettings/iterations?$timeframe=current&api-version=6.0"
$Result = Invoke-RestMethod -Uri $CurrentIterApiCall -Method get -Headers $AzureDevOpsAuthenicationHeader
$CurrentIterId=$Result.Value.id

Write-Host "got current iteration " $Result.Value.name " with id " $CurrentIterId

$Path = "./ci/ReleaseNotes.md"

$CurrentIterWiApiCall="https://dev.azure.com/nepumuk/Notes/_apis/work/teamsettings/iterations/$CurrentIterId/workitems?api-version=6.0-preview.1"
$Result = Invoke-RestMethod -Uri $CurrentIterWiApiCall -Method GET -Headers $AzureDevOpsAuthenicationHeader

Write-Host "got workitems for iteration " $Result.Value.name

$FixedBugs=@()
$NewFeatures=@()
$NewImprovements=@()

Write-Host "analyzing workitems"
foreach ($WiRelation in $Result.workItemRelations) {
	$WiId=$WiRelation.target.id
	$WiApiCall="https://dev.azure.com/nepumuk/Notes/_apis/wit/workItems/$WiId"
	$Result = Invoke-RestMethod -Uri $WiApiCall -Method GET -Headers $AzureDevOpsAuthenicationHeader
	if ($Result.fields."System.State" -eq "Resolved" -or $Result.fields."System.State" -eq "Closed" ){
		Write-Host $WiId
		if ($Result.fields."Custom.e9e4fb1f-788b-4bbf-afd5-2f14d4be2bdd" -ne $null){
			$NewImprovements+=$Result.fields."Custom.e9e4fb1f-788b-4bbf-afd5-2f14d4be2bdd"
		}if ($Result.fields."Custom.ReleaseNotesBugs" -ne $null){
			$FixedBugs += $Result.fields."Custom.ReleaseNotesBugs"
		}
		if ($Result.fields."Custom.ReleaseNotesFeature" -ne $null){
			$NewFeatures+=$Result.fields."Custom.ReleaseNotesFeature"
		}
	}
}
Write-Host "creating release notes"

# "<en-US>" | Set-Content -Path $Path
if ($NewFeatures.Length -gt 0){
	"# New Features" | Add-Content -Path $Path
	foreach ($Feature in $NewFeatures) {
		"* " + $Feature.SubString(5,$Feature.length - 5-6)  | Add-Content -Path $Path
	}
	"" | Add-Content -Path $Path
}

if ($NewImprovements.Length -gt 0){
	"# What's new"  | Add-Content -Path $Path
	foreach ($Improvement in $NewImprovements) {
		"* " + $Improvement.SubString(5,$Improvement.length - 5-6) | Add-Content -Path $Path
	}
	"" | Add-Content -Path $Path
}
if ($FixedBugs.Length -gt 0){
	"# What's fixed"  | Add-Content -Path $Path
	foreach ($Bugfix in $FixedBugs) {
		"* " + $Bugfix.SubString(5,$Bugfix.length - 5-6)  | Add-Content -Path $Path
	}
}
# "</en-US>" | Add-Content -Path $Path


Write-Host "done..."