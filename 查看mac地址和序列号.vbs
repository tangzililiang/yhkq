REM ��ȡ����IP��ַ���кź�MAC��ַ
'��YH���ڹ��ߣ�
Function GetSNMAC(ComputerName) 
Dim objWMIService,colItems,objItem,objAddress,MAC,SN,IP
Set objWMIService = GetObject("winmgmts://" & ComputerName & "/root/cimv2")

Set colItems = objWMIService.ExecQuery("Select * From Win32_NetworkAdapterConfiguration Where IPEnabled = True")
For Each objItem in colItems
	 For Each objAddress in objItem.IPAddress
		  If objAddress <> "" then
			  MAC = "MAC��ַ:" & objItem.MACAddress
			  IP="IP��ַ:"&objAddress
			  Exit For
		  End If  
	 Next
 Exit For
Next

Set colItems = objWMIService.ExecQuery("Select * From Win32_BaseBoard")
for each item in colItems
	SN="���к�:" & item.SerialNumber
next

Msgbox IP&" "&MAC&" "&SN

End Function

GetSNMAC(".")