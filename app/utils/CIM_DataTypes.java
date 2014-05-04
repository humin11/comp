package utils;

import play.Logger;

import javax.cim.*;
import javax.wbem.CloseableIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

/**
 * Created by Humin on 4/26/14.
 */
public class CIM_DataTypes
{
    Calendar realCal = null;

    public CIM_DataTypes()
    {

    }

    public CIMDateTime getCIMInstancePropertyDateTime(CIMInstance cimInstance, String propertyName)
    {
        CIMDateTime propertyValue = null;

        CIMProperty property = cimInstance.getProperty(propertyName);

        if (property!=null && property.getValue() != null)
        {
            if ((property.getValue() instanceof CIMDateTime))
            {
                propertyValue = (CIMDateTime)property.getValue();
            }
            else
            {
                try
                {
                    String strValue = (String)property.getValue();

                    propertyValue = new CIMDateTimeAbsolute(strValue);
                }
                catch (Exception e)
                {
                    Logger.error("ERROR:{}", e);
                }
            }

        }
        else
        {
            Logger.info(propertyName + " does not exist");
        }
        return propertyValue;
    }

    public Boolean getCIMInstancePropertyBooleanValue(CIMInstance cimInstance, String propertyName)
    {
        Boolean propertyValue = null;

        CIMProperty property = cimInstance.getProperty(propertyName);
        if (property!=null && property.getValue() != null)
        {
            if ((property.getValue() instanceof Boolean))
            {
                propertyValue = (Boolean)property.getValue();
            }
            else
            {
                try
                {
                    String strValue = (String)property.getValue();

                    propertyValue = new Boolean(strValue);
                }
                catch (Exception e)
                {
                    Logger.error("ERROR", e);
                }
            }

        }
        else
        {
            Logger.info(propertyName + " does not exist");
        }
        return propertyValue;
    }

    public UnsignedInteger64 getCIMInstancePropertyUnsignedInt64Value(CIMInstance cimInstance, String propertyName)
    {
        UnsignedInteger64 propertyValue = null;

        CIMProperty property = cimInstance.getProperty(propertyName);

        if (property!=null && property.getValue() != null)
        {
            if ((property.getValue() instanceof UnsignedInteger64))
            {
                propertyValue = (UnsignedInteger64)property.getValue();
            }
            else
            {
                try
                {
                    String strValue = (String)property.getValue();

                    propertyValue = new UnsignedInteger64(strValue);
                }
                catch (Exception e)
                {
                    Logger.info("ERROR", e);
                }
            }

        }
        else
        {
            Logger.info(propertyName + " does not exist");
        }
        return propertyValue;
    }

    public UnsignedInteger32 getCIMInstancePropertyUnsignedInt32Value(CIMInstance cimInstance, String propertyName)
    {
        UnsignedInteger32 propertyValue = null;

        CIMProperty property = cimInstance.getProperty(propertyName);
        if (property!=null && property.getValue() != null)
        {
            if ((property.getValue() instanceof UnsignedInteger32))
            {
                propertyValue = (UnsignedInteger32)property.getValue();
            }
            else
            {
                try
                {
                    String strValue = (String)property.getValue();

                    propertyValue = new UnsignedInteger32(strValue);
                }
                catch (Exception e)
                {
                    Logger.error("ERROR", e);
                }
            }

        }
        else
        {
            Logger.info(propertyName + " does not exist");
        }
        return propertyValue;
    }

    public UnsignedInteger16 getCIMInstancePropertyUnsignedInt16Value(CIMInstance cimInstance, String propertyName)
    {
        UnsignedInteger16 propertyValue = null;

        CIMProperty property = cimInstance.getProperty(propertyName);
        if (property!=null && property.getValue() != null)
        {
            if ((property.getValue() instanceof UnsignedInteger16))
            {
                propertyValue = (UnsignedInteger16)property.getValue();
            }
            else
            {
                try
                {
                    String strValue = (String)property.getValue();

                    propertyValue = new UnsignedInteger16(strValue);
                }
                catch (Exception e)
                {
                    Logger.error("Error", e);
                }
            }

        }
        else
        {
            Logger.info(propertyName + " does not exist");
        }
        return propertyValue;
    }

    public UnsignedInteger8 getCIMInstancePropertyUnsignedInt8Value(CIMInstance cimInstance, String propertyName)
    {
        UnsignedInteger8 propertyValue = null;

        CIMProperty property = cimInstance.getProperty(propertyName);
        if (property!=null && property.getValue() != null)
        {
            if ((property.getValue() instanceof UnsignedInteger8))
            {
                propertyValue = (UnsignedInteger8)property.getValue();
            }
            else
            {
                try
                {
                    String strValue = (String)property.getValue();

                    propertyValue = new UnsignedInteger8(strValue);
                }
                catch (Exception e)
                {
                    Logger.error("ERROR", e);
                }
            }

        }
        else
        {
            Logger.info(propertyName + " does not exist");
        }
        return propertyValue;
    }

    public CIMObjectPath getCIMInstancePropertyObjectPath(CIMInstance cimInstance, String propertyName)
    {
        CIMObjectPath propertyValue = null;

        CIMProperty property = cimInstance.getProperty(propertyName);
        if (property!=null && property.getValue() != null)
        {
            try
            {
                propertyValue = (CIMObjectPath)property.getValue();
            }
            catch (Exception e)
            {
                Logger.info("Error", e);
            }

        }
        else
        {
            Logger.info(propertyName + " does not exist");
        }
        return propertyValue;
    }

    public String getCIMInstancePropertyValueString(CIMInstance cimInstance, String propertyName)
    {
        String propertyValue = null;

        CIMProperty property = cimInstance.getProperty(propertyName);
        if (property!=null && property.getValue() != null)
        {
            try
            {
                propertyValue = (String)property.getValue();
            }
            catch (Exception e)
            {
                Logger.error("ERROR", e);
            }

        }
        else
        {
            Logger.info(propertyName + " does not exist");
        }
        return propertyValue;
    }

    public Vector getCIMInstancePropertyValueVector(CIMInstance cimInstance, String propertyName)
    {
        Vector propertyValue = null;

        CIMProperty property = cimInstance.getProperty(propertyName);
        if (property!=null && property.getValue() != null)
        {
            try
            {
                propertyValue = (Vector)property.getValue();
            }
            catch (Exception e)
            {
                Logger.info("Error", e);
            }

        }
        else
        {
            Logger.info(propertyName + " does not exist");
        }
        return propertyValue;
    }

    public List<CIMInstance> iteratorToInstanceArrayList(CloseableIterator<CIMInstance> pIter)
    {
        List instList = new ArrayList();

        while (pIter.hasNext())
        {
            instList.add((CIMInstance)pIter.next());
        }
        return instList;
    }

    public UnsignedInteger16[] getUint16ArrayPropertyValue(CIMInstance pInst, String pPropName)
            throws Exception
    {
        UnsignedInteger16[] propertyValue = (UnsignedInteger16[])null;

        CIMProperty property = pInst.getProperty(pPropName);
        if (property!=null && property.getValue() != null)
        {
            if ((property.getValue() instanceof UnsignedInteger16[]))
            {
                propertyValue = (UnsignedInteger16[])property.getValue();
            }
            else
            {
                try
                {
                    String[] strArray = (String[])property.getValue();

                    propertyValue = stringArrayToUInt16Array(strArray);
                }
                catch (Exception e)
                {
                    Logger.error("ERROR", e);
                }
            }

        }
        else
        {
            Logger.info(pPropName + " does not exist");
        }
        return propertyValue;
    }

    public UnsignedInteger16[] stringArrayToUInt16Array(String[] pStringArray)
            throws NumberFormatException
    {
        UnsignedInteger16[] retArray = (UnsignedInteger16[])null;
        if (pStringArray != null) {
            retArray = new UnsignedInteger16[pStringArray.length];

            for (int i = 0; i < retArray.length; i++)
            {
                retArray[i] = new UnsignedInteger16(pStringArray[i]);
            }
        }
        return retArray;
    }

    public String[] getStringArrayPropertyValue(CIMInstance pInst, String pPropName)
            throws Exception
    {
        String[] propertyValue = (String[])null;

        CIMProperty property = pInst.getProperty(pPropName);
        if (property!=null && property.getValue() != null)
        {
            if ((property.getValue() instanceof String[]))
            {
                propertyValue = (String[])property.getValue();
            }
            else
            {
                try
                {
                    String[] strArray = (String[])property.getValue();

                    propertyValue = strArray;
                }
                catch (Exception e)
                {
                    Logger.error("ERROR", e);
                }
            }

        }
        else
        {
            Logger.info(pPropName + " does not exist");
        }
        return propertyValue;
    }

    public Calendar getCalendarFromCIMDateTime(CIMDateTime cdt)
            throws NumberFormatException
    {
        Calendar cal = Calendar.getInstance();
        if (cdt != null) {
            String statisticTimeString = cdt.toString();
            CIMDateTimeAbsolute cdtAbs = new CIMDateTimeAbsolute(statisticTimeString);

            cal.set(1, cdtAbs.getYear());
            cal.set(2, cdtAbs.getMonth() - 1);
            cal.set(5, cdtAbs.getDay());
            cal.set(11, cdtAbs.getHour());
            cal.set(12, cdtAbs.getMinute());
            cal.set(13, cdtAbs.getSecond());
            Logger.info("*******************************************************************");
            Logger.info("Calendar.YEAR: " + cdtAbs.getYear());
            Logger.info("Calendar.MONTH: " + cdtAbs.getMonth());
            Logger.info("Calendar.DAY_OF_MONTH: " + cdtAbs.getDay());
            Logger.info("Calendar.HOUR_OF_DAY: " + cdtAbs.getHour());
            Logger.info("Calendar.MINUTE: " + cdtAbs.getMinute());
            Logger.info("Calendar.SECOND: " + cdtAbs.getSecond());
            Logger.info("*******************************************************************");
        }
        else
        {
            Logger.info(cdt + " does not exist");
        }
        return cal;
    }
}