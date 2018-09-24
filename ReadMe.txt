================================================================================================================================================================
Framing Center Module
================================================================================================================================================================

#Frame Type Information 
(e.g. Single Mounted,Double Mounted,Direct Framing,Lamination Framing)
(Validation: Frame Type must be unique and mandatory)

#Frame Number 
(Each Frame is identified as unique frame Number
(Validation: Frame Number must be unique and mandatory)

#Frame Size 
(Frame Size consist of width and height of frame e.g 6*8,12*18 etc.)
(Validation: COMBINATION of Frame Width & Frame Height & MeasurementType must be unique and mandatory)

#Frame Thickness 
(Frame thickness can be different per frame e.g. 1.0 inch,1.25 inch,1.50 inch,2.50 inch etc.)
(Validation: COMBINATION of Frame Thickness & Frame MeasurementType must be unique and mandatory)

#Frame Client Type
(Frame Client Type consist of what type of client. e.g. Photographer or Regular Customer (only 2 till now),Frame Price chart is decided depending upon Client Type for eg (Photographer 30% profit margin,Regular customer 70% profit margin))
(Validation: Frame Client Type must be unique and mandatory)

#Frame Detail 
Frame Detail must be uniquly identified Frame which has following attribute unique in it. (Frame Type,Frame Number,Frame Size,Frame Thickness)
No Validation 

#Frame Rate
Frame Rate is depending upon following attribute 
(Frame Detail (Which has all unique detail about frame) + Frame Client Type (Depending upon client Type Rate is decided as per 30% , 70% margin) + Price (Actual Price of Frame))
No Validation
