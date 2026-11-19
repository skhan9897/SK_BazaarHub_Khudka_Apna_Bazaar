import React from 'react';
import { ProductItem } from './ProductCard';

export interface CartItem {
  product: ProductItem;
  quantity: number;
}

interface CartDrawerProps {
  isOpen: boolean;
  onClose: () => void;
  cartItems: CartItem[];
  onUpdateQuantity: (productId: number, qty: number) => void;
  onRemoveItem: (productId: number) => void;
  onProceedToCheckout: () => void;
}

export const CartDrawer: React.FC<CartDrawerProps> = ({
  isOpen,
  onClose,
  cartItems,
  onUpdateQuantity,
  onRemoveItem,
  onProceedToCheckout,
}) => {
  if (!isOpen) return null;

  const totalAmount = cartItems.reduce(
    (sum, item) => sum + item.product.basePrice * item.quantity,
    0
  );

  return (
    <div className="fixed inset-0 z-50 overflow-hidden bg-black/50 backdrop-blur-sm flex justify-end transition-opacity">
      <div className="w-full max-w-md bg-white h-full shadow-2xl flex flex-col justify-between">
        {/* Header */}
        <div className="p-4 border-b flex justify-between items-center bg-surface-bg">
          <div className="flex items-center space-x-2">
            <span className="text-xl">🛒</span>
            <h2 className="font-bold text-lg text-surface-dark">Your Shopping Cart</h2>
            <span className="bg-brand-500 text-white text-xs font-bold px-2 py-0.5 rounded-full">
              {cartItems.length} items
            </span>
          </div>
          <button
            onClick={onClose}
            className="w-8 h-8 rounded-full bg-gray-200 text-gray-700 font-bold hover:bg-gray-300 transition flex items-center justify-center"
          >
            ✕
          </button>
        </div>

        {/* Cart Item List */}
        <div className="flex-1 overflow-y-auto p-4 space-y-4">
          {cartItems.length === 0 ? (
            <div className="h-full flex flex-col items-center justify-center text-center text-gray-400">
              <span className="text-5xl mb-3">🛍️</span>
              <p className="font-bold text-gray-700">Your cart is currently empty</p>
              <p className="text-xs text-gray-500 mt-1">Explore our catalog and add amazing deals!</p>
            </div>
          ) : (
            cartItems.map((item) => (
              <div
                key={item.product.id}
                className="flex items-center space-x-3 p-3 bg-surface-bg rounded-xl border border-gray-100"
              >
                <img
                  src={item.product.mainImageUrl}
                  alt={item.product.name}
                  className="w-16 h-16 object-contain bg-white rounded-lg p-1 border"
                />
                <div className="flex-1">
                  <h4 className="font-bold text-xs text-gray-900 line-clamp-1">
                    {item.product.name}
                  </h4>
                  <p className="text-brand-600 font-extrabold text-sm mt-0.5">
                    ₹{item.product.basePrice.toLocaleString('en-IN')}
                  </p>
                  {/* Quantity adjustment */}
                  <div className="flex items-center space-x-2 mt-2">
                    <button
                      onClick={() => onUpdateQuantity(item.product.id, item.quantity - 1)}
                      className="w-6 h-6 rounded bg-gray-200 hover:bg-gray-300 text-gray-800 font-bold text-xs"
                    >
                      -
                    </button>
                    <span className="text-xs font-bold px-1">{item.quantity}</span>
                    <button
                      onClick={() => onUpdateQuantity(item.product.id, item.quantity + 1)}
                      className="w-6 h-6 rounded bg-gray-200 hover:bg-gray-300 text-gray-800 font-bold text-xs"
                    >
                      +
                    </button>
                  </div>
                </div>
                <button
                  onClick={() => onRemoveItem(item.product.id)}
                  className="text-red-500 hover:text-red-700 p-1 text-xs"
                >
                  🗑️
                </button>
              </div>
            ))
          )}
        </div>

        {/* Footer & Checkout Action */}
        {cartItems.length > 0 && (
          <div className="p-4 border-t bg-white space-y-3">
            <div className="flex justify-between items-center text-sm">
              <span className="text-gray-500 font-medium">Subtotal</span>
              <span className="font-black text-gray-900 text-base">
                ₹{totalAmount.toLocaleString('en-IN')}
              </span>
            </div>
            <p className="text-[11px] text-gray-400 text-center">
              Taxes & delivery fees calculated at checkout.
            </p>
            <button
              onClick={onProceedToCheckout}
              className="w-full bg-gradient-to-r from-brand-500 to-brand-700 text-white font-bold py-3 rounded-xl hover:shadow-lg hover:shadow-brand-500/25 transition active:scale-[0.99]"
            >
              Proceed to Checkout →
            </button>
          </div>
        )}
      </div>
    </div>
  );
};
