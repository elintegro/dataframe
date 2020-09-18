import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.vue.VueJsBuilder
import com.elintegro.erf.widget.vue.WidgetVue
import org.apache.commons.lang.WordUtils
import org.springframework.context.i18n.LocaleContextHolder

class CarouselWidgetVue extends WidgetVue{
    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        String modelString = getModelString(dataframe, field)
        String height = field.height
        String content = field.content
        String html = """
                   <v-carousel
                      cycle
                      show-arrows-on-hover
                      hide-delimiter-background
                      height= "$height"
                   >   
                      <v-carousel-item
                           v-for="(item,i) in ${modelString}_items"
                           :key="i"
                      >
                        ${content}
                      </v-carousel-item>
                   </v-carousel>
                """

        return html
    }
    String getStateDataVariable(DataframeVue dataframe, Map field) {
        String fldName = dataframe.getDataVariableForVue(field)
        String quote = getMessageSource().getMessage("quote", null, "Quote", LocaleContextHolder.getLocale())
        String name = getMessageSource().getMessage("name", null, "Name", LocaleContextHolder.getLocale())
        String title = getMessageSource().getMessage("title", null, "Title", LocaleContextHolder.getLocale())
        String quote1 = getMessageSource().getMessage("quote1", null, "Quote1", LocaleContextHolder.getLocale())
        String name1 = getMessageSource().getMessage("name1", null, "Name1", LocaleContextHolder.getLocale())
        String title1 = getMessageSource().getMessage("title1", null, "Title1", LocaleContextHolder.getLocale())
        return """
                                                    ${fldName}_items: [
                                                                {
                                                                    quote: "${quote}",
                                                                    name: "${name}",
                                                                    title: "${title}",
                                                                    src:'assets/home/shai.png'
                                                                },
                                                                {
                                                                    quote: "${quote1}",
                                                                    name: "${name1}",
                                                                    title: "${title1}",
                                                                    src:'assets/home/eugene.png'
                                                                    
                                                                },
                                                    ],
                                                """

    }
}
