[CmdletBinding()]
param()

$variables = Get-Childitem -Path Env:* # | Sort-Object Name


foreach ($variable in $variables) {
	if ($variable.name -eq "System.AccessToken"){
		$Token =  $variable.Value
	}
}

$AzureDevOpsAuthenicationHeader = @{Authorization = 'Basic ' + [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes(":$($Token)")) }



$CurrentIterApiCall = "https://dev.azure.com/nepumuk/Notes/Notes%20Team/_apis/work/teamsettings/iterations?$timeframe=current&api-version=6.0"

$Result = Invoke-RestMethod -Uri $CurrentIterApiCall -Method get -Headers $AzureDevOpsAuthenicationHeader

$CurrentIterId=$Result.Value.id

$Path = "./ci/ReleaseNotes.md"

#Write-Host "Iteration" $Result.Value.name "id: " $CurrentIterId
$CurrentIterWiApiCall="https://dev.azure.com/nepumuk/Notes/_apis/work/teamsettings/iterations/$CurrentIterId/workitems?api-version=6.0-preview.1"
#Write-Host "API:" $CurrentIterWiApiCall
$Result = Invoke-RestMethod -Uri $CurrentIterWiApiCall -Method GET -Headers $AzureDevOpsAuthenicationHeader

#$Result.workItemRelations

$FixedBugs=@()
$NewFeatures=@()
$NewImprovements=@()

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
"" | Set-Content -Path $Path
if ($NewFeatures.Length -gt 0){
	"#New Features" | Add-Content -Path $Path
	foreach ($Feature in $NewFeatures) {
		"*" + $Feature.SubString(5,$Feature.length - 5-6)  | Add-Content -Path $Path
	}
	"" | Add-Content -Path $Path
}

if ($NewImprovements.Length -gt 0){
	"#Improvements"  | Add-Content -Path $Path
	foreach ($Improvement in $NewImprovements) {
		"*" + $Improvement.SubString(5,$Improvement.length - 5-6) | Add-Content -Path $Path
	}
	"" | Add-Content -Path $Path
}
if ($FixedBugs.Length -gt 0){
	"#Bugfixes"  | Add-Content -Path $Path
	foreach ($Bugfix in $FixedBugs) {
		"*" + $Bugfix.SubString(5,$Bugfix.length - 5-6)  | Add-Content -Path $Path
	}
}
