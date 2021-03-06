
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.widget.vue.CollectionWidgetVue
import org.apache.commons.lang.WordUtils
import org.springframework.context.i18n.LocaleContextHolder

class CarouselWidgetVue extends CollectionWidgetVue{

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        def fldNameDefault = WordUtils.capitalizeFully(fldName);
        String labelCode = field.labelCode ?: fldName
        String label = getMessageSource().getMessage(labelCode, null, fldNameDefault, LocaleContextHolder.getLocale())
        String html = getHtmlStructure(dataframe, field, fldName, label)
        return html
    }
    String getVueDataVariable(DataframeVue dataframe, Map field) {
        return """
                """
    }

    private String getHtmlStructure(DataframeVue dataframe, Map field, String fldName, String label) {
        String itemsStr = getFieldJSONItems(field)
        String onClick = field.OnClick?:""
        String height = field.height
        String content = field.content
        """
               




<v-carousel
                      cycle
                      show-arrows-on
                      hide-delimiter-background
                      height= "$height"
                      ${getAttr(field)}
                   >   
                      <v-carousel-item
                           v-for="(item,i) in ${itemsStr}"
                           :key="i"
                      >
                        ${content}
                      </v-carousel-item>
                   </v-carousel>
        """

    }
}
