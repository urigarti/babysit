<?php 

include ('config.inc.php');
include ('password_encryption.php');

$queryExistingUser = "select username,password,pass_salt from babysit.users where username='$_GET[username]'";
// echo $queryExistingUser;
$db = run_query($queryExistingUser);
// echo mysqli_affected_rows($db->getConnection());
if($db->num_rows == 1) {
	// Check for password
	$row = mysqli_fetch_row($db);
	$new_hash = validate_password($_GET['password'], $row[1]);
	if($new_hash == 1){
		$response["status"] = 1;
		$response["message"] = "User logged in: ".$_GET["username"];
		echo json_encode($response);
	}
	else {
		$response["status"] = 0;
		$response["message"] = "Wrong password: ".$_GET["password"];
		echo json_encode($response);
	}
}
else{
	if($db->num_rows == 0) {
		$response["status"] = 0;
		$response["message"] = "User could not be found: ".$_GET["username"];
		echo  json_encode($response);
	}
}
?>