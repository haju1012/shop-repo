package de.shop.bestellverwaltung.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author <a>Team 8</a>
 */
@Converter(autoApply = true)
public class TransportTypeConverter implements AttributeConverter<TransportType, String> {
	@Override
	public String convertToDatabaseColumn(TransportType transportType) {
		return transportType.getDbString();
	}

	@Override
	public TransportType convertToEntityAttribute(String dbString) {
		return TransportType.build(dbString);
	}
}
