<?php
 


 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();

$response = array();


 $email = $_POST['email'];
     
 $res = mysql_query("SELECT phone FROM User WHERE emailId = '$email' ")or die(mysql_error()) ;
 
 if ($res) {
	$result=mysql_fetch_array($res);
	$result=$result['phone'];

    if(isset($result) && trim($result) != '' )
echo $result;

}

?>
