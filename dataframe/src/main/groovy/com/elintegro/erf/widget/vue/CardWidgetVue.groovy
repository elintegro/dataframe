/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development.
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works.
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */



package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.vue.DataframeVue
import grails.util.Holders
import groovy.util.logging.Slf4j
import org.apache.commons.lang.WordUtils
import org.springframework.context.i18n.LocaleContextHolder


/**
 * Created by pangenirabindra on Jan, 2021.
 */
@Slf4j

class CardWidgetVue extends CollectionWidgetVue {
    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        def fldNameDefault = WordUtils.capitalizeFully(fldName);
        String labelCode = field.labelCode?:fldName
        String label = getMessageSource().getMessage(labelCode, null, fldNameDefault, LocaleContextHolder.getLocale())
        String html = getHtmlStructure(dataframe, field, fldName, label)
        return html
    }
    private String getHtmlStructure(DataframeVue dataframe, Map field, String fldName, String label){
        String multiple = field.multiple?"multiple":''
        String displayMember = field.displayMember?:'name'
        String valueMember = field.valueMember?:'id'
        String cardAction = field.cardAction?:""
        String cardActionCode = cardAction?""" {{item.$cardAction}} """:""
        String vuetifyIconForCardAction = field.vuetifyIconForCardAction?:""
        String loader = """ <template v-slot:placeholder>
                                        <v-row
                                          class="fill-height ma-0"
                                          align="center"
                                          justify="center"
                                        >
                                          <v-progress-circular
                                            indeterminate
                                            color="#ffc526"
                                          ></v-progress-circular>
                                        </v-row>
                                        </template>
                           """
        String iconCode = vuetifyIconForCardAction?"""<v-icon left>$vuetifyIconForCardAction</v-icon>""":""
        String cardActionAttr = field.cardActionAttr?:""
        String cardActionAndIconCode = ''
        if(cardAction || vuetifyIconForCardAction){
            cardActionAndIconCode = """  <v-btn text color="black" class="cardActions" $cardActionAttr>
                                           $iconCode
                                           $cardActionCode
                                       </v-btn> """
        }
        boolean cardImageAndText = field.cardImageAndText?:false
        String pictureForVendorType = field.pictureForVendorType?:""
        String cardImageTextCode = cardImageAndText?""" 
                                        <v-btn text color="black" class='cardImageTextCode'>
                                          <v-img height='14' width='14' :src="item.${pictureForVendorType}">${loader}</v-img>
                                          <div class="d-flex pa-0 cardImageText">{{item.VendorType}}</div> 
                                       </v-btn> 
                                    """:""
        String cardTitle = field.cardTitle?:""
        String cardDescription = field.cardDescription?:""
        String cardAvatar = field.cardAvatar?:""
        String avatarAttr = field.avatarAttr?:""
        String itemsStr = getFieldJSONItems(field)
        String cardAttr = field.cardAttr?:""
        String script = field.script?:""
        boolean showPriceLevel = field.showPriceLevel?:false
        String priceLevelCode = showPriceLevel?"""<div class="d-flex cardWidgetPriceLevel align-center">
                                       <span class="caption font-weight-bold " :style="{'color':item.PriceLevelOneColor}">\$</span>
                                       <span class="caption font-weight-bold" :style="{'color':item.PriceLevelTwoColor}">\$</span>
                                       <span class="caption font-weight-bold" :style="{'color':item.PriceLevelThreeColor}">\$</span>
                                  </div>""":""
        String modelString = getFieldJSONModelNameVue(field)
        String descriptionFirstIcon = field.descriptionFirstIcon?"""<v-img height='16' width='16' :src="item.${field.descriptionFirstIcon}" class='recipe-type-categroy'>${loader}</v-img>""":''
        String descriptionSecondIcon = field.descriptionSecondIcon?"""<v-img height='16' width='16' :src="item.${field.descriptionSecondIcon}" class='recipe-type-categroy'>${loader}</v-img>""":''

        return """
                <v-flex v-for="item in ${itemsStr}" :key="item.id">
                      <v-card color="white" rounded elevation="1"  class= "card-list" style="border-radius:5px !important;" height='90' @click="$script"  $cardAttr>
                            <v-row class="ma-0">
                                <v-col cols="4" class="pa-0 thumbnailCol"> 
                                    <v-img style="border-radius:5px 5px 5px 5px;" $avatarAttr :src="item.$cardAvatar" class="imageForItems">${loader}</v-img>  
                                </v-col>       
                                <v-col cols="8" class="detailCol ">
                                    <div class="d-flex pa-0 pb-1 cardTitle" v-html="item.$cardTitle"></div>
                                    <div class="d-flex cardDesciption text-left" v-html="item.$cardDescription"></div>
                                    <div class="d-flex card_button_Icon">
                                       $cardActionAndIconCode 
                                       $priceLevelCode
                                       $descriptionSecondIcon
                                       $descriptionFirstIcon
                                       $cardImageTextCode
                                    </div>     
                                </v-col>
                            </v-row>
                      </v-card>
                </v-flex>
     
        """
    }
}
