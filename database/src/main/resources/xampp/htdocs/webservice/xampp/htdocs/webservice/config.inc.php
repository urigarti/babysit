<?php


class Database {
	var  $username = "urigarti";
	var $password = "Bcat3BnNM78KyQxs";
	var $host = "127.0.0.1";
	var $dbname = "babysit";
// 	private $options = array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8');

	var $db;
	var $connection;

	function __construct() {
// 		$this->connection = new connection;
// 		$this->connection = mysql_connect($this->host,$this->username,$this->password,$this->dbname);
// echo $this->host  + "zxdgfzxfg";
// echo "mysql:host=".$this->host.";dbname=".$this->dbname.";charset=utf8";
// 		$this->connection = new PDO("mysql:host=".$this->host.";dbname=".$this->dbname.";charset=utf8", $this->username, $this->password, $this->options);
		$this->connection = mysqli_connect($this->host,$this->username,$this->password,$this->dbname);
	}

	function __destruct() {
		$this->db = null;
	}

	public function getConnection() {
		if ($this->db == null) {
			$this->db = new Database();
		}
		return $this->db->connection;
	}
	
	public function closeConnection() {
		mysqli_close($this->connection);
	}
	
	function getAffectedRowsNum() {
		return mysqli_affected_rows($this->getConnection());
	}
	
	function run_query_internal($query) {
		try {
			mysqli_query($this->getConnection(),$query);
			/* if($l_stmt = $this->getConnection()->prepare($query)) {
				// 			$l_rows = $l_stmt->execute();
				$stmt = $this->getConnection()->query($query);
				if($stmt == null) {
// 					$response["status"] = 0;
// 					$response["message"] = "failed database query.";
// 					return $response;
					return $stmt; 
				}
// 				$response["status"] = 1;
// 				$response["message"] = "query successful.";
// 				echo 'user updated';
// 				return $response;
// 				return $stmt;
				$this->closeConnection();
			} */
		}
		catch(Exception $pdoe) {
			echo "aaa";
		}
	}
}

// 	    // These variables define the connection information for your MySQL database
// 		$username = "urigarti";
// 	    $password = "Bcat3BnNM78KyQxs";
// 	    $host = "localhost";
// 	    $dbname = "babysit";
	    $options = array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8');
	      
	    try
	    {
	        // This statement opens a connection to your database using the PDO library
	        // PDO is designed to provide a flexible interface between PHP and many
	        // different types of database servers.  For more information on PDO:
	        // http://us2.php.net/manual/en/class.pdo.php
// 	        $db = new PDO("mysql:host={$host};dbname={$dbname};charset=utf8", $username, $password, $options);
// 			$db = mysql_connect($host,$username,$password,$dbname);
	    }
	    catch(PDOException $ex)
	    {
	        die("Failed to connect to the database: " . $ex->getMessage());
	    }
	      
	    // This statement configures PDO to throw an exception when it encounters
	    // an error.  This allows us to use try/catch blocks to trap database errors.
// 	    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	      
	    // This statement configures PDO to return database rows from your database using an associative
	    // array.  This means the array will have string indexes, where the string value
	    // represents the name of the column in your database.
// 	    $db->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
	      
	    // This block of code is used to undo magic quotes.  Magic quotes are a terrible
	    // feature that was removed from PHP as of PHP 5.4.  However, older installations
	    // of PHP may still have magic quotes enabled and this code is necessary to
	    // prevent them from causing problems.  For more information on magic quotes:
	    // http://php.net/manual/en/security.magicquotes.php
	    if(function_exists('get_magic_quotes_gpc') && get_magic_quotes_gpc())
	    {
	        function undo_magic_quotes_gpc(&$array)
	        {
	            foreach($array as &$value)
	            {
	                if(is_array($value))
	                {
	                    undo_magic_quotes_gpc($value);
	                }
	                else
	                {
	                    $value = stripslashes($value);
	                }
	            }
	        }
	      
	        undo_magic_quotes_gpc($_POST);
	        undo_magic_quotes_gpc($_GET);
	        undo_magic_quotes_gpc($_COOKIE);
	    }
	      
	    // This tells the web browser that your content is encoded using UTF-8
	    // and that it should submit content back to you using UTF-8
	    header('Content-Type: text/html; charset=utf-8');
	      
	    session_start();
	 
	    // Note that it is a good practice to NOT end your PHP files with a closing PHP tag.
	    // This prevents trailing newlines on the file from being included in your output,
	    // which can cause problems with redirecting users.
	 
	    function run_query($query) {
	    	$db = new Database();
	    	$queryResult = $db->run_query_internal($query);
	    	return mysqli_query($db->getConnection(),$query);
	    }
	  
	?>