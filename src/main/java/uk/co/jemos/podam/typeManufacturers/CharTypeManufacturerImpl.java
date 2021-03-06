package uk.co.jemos.podam.typeManufacturers;

import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.common.PodamCharValue;

import java.lang.annotation.Annotation;

/**
 * Default character type manufacturer.
 *
 * Created by tedonema on 17/05/2015.
 *
 * @since 6.0.0.RELEASE
 */
public class CharTypeManufacturerImpl extends AbstractTypeManufacturer {

    /**
     * {@inheritDoc}
     */
    @Override
    public Character getType(TypeManufacturerParamsWrapper wrapper) {

        super.checkWrapperIsValid(wrapper);

        DataProviderStrategy strategy = wrapper.getDataProviderStrategy();

        Character retValue = null;

        for (Annotation annotation : wrapper.getAttributeMetadata().getAttributeAnnotations()) {

            if (PodamCharValue.class.isAssignableFrom(annotation.getClass())) {
                PodamCharValue annotationStrategy = (PodamCharValue) annotation;

                char charValue = annotationStrategy.charValue();
                if (charValue != ' ') {
                    retValue = charValue;

                } else {

                    char minValue = annotationStrategy.minValue();
                    char maxValue = annotationStrategy.maxValue();

                    // Sanity check
                    if (minValue > maxValue) {
                        maxValue = minValue;
                    }

                    retValue = strategy.getCharacterInRange(minValue, maxValue,
                            wrapper.getAttributeMetadata());

                }

                break;

            }
        }

        if (retValue == null) {
            retValue = strategy.getCharacter(wrapper.getAttributeMetadata());
        }

        return retValue;
    }
}
