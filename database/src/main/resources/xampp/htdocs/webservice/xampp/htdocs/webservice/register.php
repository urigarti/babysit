<?php 

include ('config.inc.php');
include ('password_encryption.php');

$queryExistingUser = "select username from babysit.users where username='$_GET[username]'";
$db = run_query($queryExistingUser);
if($db->num_rows == 1) {
	$response["status"] = 0;
	$response["message"] = "User alreasy exist: ".$_GET["username"];
	echo json_encode($response);
}
else{
	$salt = create_salt();
	$password_hash = create_hash($_GET['password'], $salt);
	
	$query = "INSERT INTO babysit.users (username, type, password, pass_salt) VALUES ('$_GET[username]', '$_GET[type]', '$password_hash',  '$salt');";
	
	$db1 = run_query($query);
	if(empty($db1)) {
		$response["status"] = 1;
		$response["message"] = "User: ".$_GET["username"]." "."was created successfully";
		echo json_encode($response);
	}
	else {
		$response["status"] = 0;
		$response["message"] = "Failed to create user: ".$_GET["username"];
		echo json_encode($response);
	}
}
return $response;
?>

<!-- <form action="text.php" method="post"> -->
<!-- 	<input type="text" name="username" value="" hidden="true"/> -->
<!-- 	<input type="text" name="type" value="" hidden="true"/> -->
<!-- 	<input type="text" name="password" value="" hidden="true"/> -->
<!-- 	</form> -->




