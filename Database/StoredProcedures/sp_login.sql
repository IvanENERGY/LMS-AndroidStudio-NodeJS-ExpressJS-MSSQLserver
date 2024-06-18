USE LMSDB;
DROP PROCEDURE if exists [Login]
GO  

CREATE PROCEDURE [Login] 
    @Ac varchar(10),   
    @Pw varchar(10) 

AS   
BEGIN
	Declare @Account_Type AS char(1)
	IF EXISTS ( SELECT 1 FROM Account WHERE Ac=@Ac AND Pw=@Pw )
		BEGIN
		--valid		
		--check account type
		IF EXISTS ( SELECT 1 FROM Staff WHERE Ac=@Ac)
			SET @Account_Type='S'
		ELSE IF EXISTS ( SELECT 1 FROM Reader WHERE Ac=@Ac)
			SET @Account_Type='R'

		--update lastlogin
		update Account 
		Set LastLogin=CAST( GETDATE() AS Date ) WHERE Ac=@Ac 
		SELECT Ac,LastLogin,@Account_Type AS AccountType from account WHERE Ac=@Ac 

		END
	ELSE
		--invalid
		SELECT 'WRONG CREDENTIALS' AS [Message]

END
GO  
--execute [Login] 'ivan','123'
--execute [Login] 'ivan2','122323'