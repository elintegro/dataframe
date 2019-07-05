/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.utils

import grails.util.Holders
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.ISODateTimeFormat

import java.text.DateFormat
import java.text.SimpleDateFormat

class DateTimdataframe
{
	
	
	public Date getDateTimeFieldsFromStringTime(String date)
	{   
		Date parsedDate=  null;
        try
        {
            String isoRegex = """^([\\+-]?\\d{4}(?!\\d{2}\\b))((-?)((0[1-9]|1[0-2])(\\3([12]\\d|0[1-9]|3[01]))?|W([0-4]\\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\\d|[12]\\d{2}|3([0-5]\\d|6[1-6])))([T\\s]((([01]\\d|2[0-3])((:?)[0-5]\\d)?|24\\:?00)([\\.,]\\d+(?!:))?)?(\\17[0-5]\\d([\\.,]\\d+)?)?([zZ]|([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?)?\$"""
            if (date.find(isoRegex)){
                DateTimeFormatter formatter = ISODateTimeFormat.dateTimeParser()
                parsedDate  = new Date(formatter.parseDateTime(date).millis)
            }else {
                String configuredDateFormat = this.getMessage(new Locale("en"),"app.date.format")
                DateFormat dateFormat = new SimpleDateFormat(configuredDateFormat);
                parsedDate  = dateFormat.parse(date);
            }

        }
		catch(Exception exception)
		{
//			log.error("Exception While parsing date",exception)
            println("Exception While parsing date " + exception)
		}
		return parsedDate;
	}
	
	public def getMessage(Locale locale, String message) {
		return Holders.grailsApplication.getParentContext().getMessage(message,null,"dd-MM-yyyy",locale)
	}
	
	

}
