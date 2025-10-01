package com.graduationproject.payment_service.config;

import com.graduationproject.payment_service.entity.PaymentStatus;
import com.graduationproject.payment_service.entity.PaymentMethod;
import com.graduationproject.payment_service.entity.TransactionStatus;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PostgreSQLEnumType implements UserType<Enum<?>> {
    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    @Override
    public Class<Enum<?>> returnedClass() {
        return (Class<Enum<?>>) (Class<?>) Enum.class;
    }

    @Override
    public boolean equals(Enum<?> x, Enum<?> y) {
        return x == y;
    }

    @Override
    public int hashCode(Enum<?> x) {
        return x != null ? x.hashCode() : 0;
    }

    @Override
    public Enum<?> nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner)
            throws SQLException {
        String name = rs.getString(position);
        if (rs.wasNull()) {
            return null;
        }

        String columnName = rs.getMetaData().getColumnName(position);
        if (columnName.equals("status")) {
            return PaymentStatus.valueOf(name);
        }
        if (columnName.equals("payment_method")) {
            return PaymentMethod.valueOf(name);
        }
        if (columnName.equals("transaction_status")) {
            return TransactionStatus.valueOf(name);
        }
        throw new HibernateException("Unknown enum type for column: " + columnName);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Enum<?> value, int index, SharedSessionContractImplementor session)
            throws SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, value.name(), Types.OTHER);
        }
    }

    @Override
    public Enum<?> deepCopy(Enum<?> value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Enum<?> value) {
        return (Serializable) value;
    }

    @Override
    public Enum<?> assemble(Serializable cached, Object owner) {
        return (Enum<?>) cached;
    }
}
