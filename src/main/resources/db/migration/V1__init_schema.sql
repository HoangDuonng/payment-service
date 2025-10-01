BEGIN;

-- Enum cho phương thức thanh toán
DO $$ BEGIN
    CREATE TYPE payment_method AS ENUM (
        'CREDIT_CARD',
        'DEBIT_CARD',
        'BANK_TRANSFER',
        'E_WALLET',
        'CASH',
        'OTHER'
    );
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

-- Enum cho trạng thái giao dịch của Payment Gateway (nếu cần chi tiết hơn trạng thái payment_status)
DO $$ BEGIN
    CREATE TYPE transaction_status AS ENUM (
        'INITIATED',    -- Giao dịch đã được khởi tạo
        'SUCCESS',      -- Giao dịch thành công
        'FAILED',       -- Giao dịch thất bại
        'PENDING',      -- Giao dịch đang chờ xử lý từ gateway
        'CANCELLED',    -- Giao dịch bị hủy
        'REFUNDED',     -- Giao dịch đã được hoàn tiền
        'CHARGEBACK'    -- Giao dịch bị yêu cầu hoàn tiền ngược (chargeback)
    );
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

DO $$ BEGIN
    CREATE TYPE payment_status AS ENUM (
        'PENDING',
        'SUCCESS',
        'FAILED',
        'CANCELLED'
    );
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

---

CREATE TABLE IF NOT EXISTS public.payments (
    id BIGSERIAL NOT NULL,
    document_id UUID DEFAULT gen_random_uuid() UNIQUE NOT NULL, -- UUID cho mỗi giao dịch payment

    booking_id BIGINT NOT NULL,          -- Liên kết với bảng bookings
    
    amount NUMERIC(12, 2) NOT NULL,      -- Số tiền của giao dịch thanh toán
    currency VARCHAR(10) DEFAULT 'VND' NOT NULL, -- Đơn vị tiền tệ, ví dụ: 'VND', 'USD'
    
    payment_method payment_method NOT NULL, -- Phương thức thanh toán
    
    status payment_status DEFAULT 'PENDING' NOT NULL, -- Trạng thái thanh toán (sử dụng lại enum từ booking hoặc định nghĩa riêng nếu cần chi tiết hơn)
    transaction_status transaction_status DEFAULT 'PENDING' NOT NULL, -- Trạng thái giao dịch chi tiết hơn với Payment Gateway (tùy chọn)
    
    transaction_code VARCHAR(255) UNIQUE, -- Mã giao dịch từ cổng thanh toán (nếu có)
    payment_gateway_response JSONB,       -- Lưu trữ phản hồi đầy đủ từ cổng thanh toán (nếu cần)
    
    paid_at TIMESTAMP,                   -- Thời điểm thanh toán thành công
    
    is_deleted BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT payments_pkey PRIMARY KEY (id)
);

---

-- Index hỗ trợ truy vấn
CREATE INDEX idx_payments_booking_id ON public.payments (booking_id);
CREATE INDEX idx_payments_status ON public.payments (status);
CREATE INDEX idx_payments_transaction_code ON public.payments (transaction_code);
CREATE INDEX idx_payments_document_id ON public.payments (document_id);
CREATE INDEX idx_payments_created_at ON public.payments (created_at);

COMMIT;
